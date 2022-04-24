package com.getir.readingisgood.service.Impl;

import com.getir.readingisgood.dao.CustomerCreateDAO;
import com.getir.readingisgood.dao.CustomerLoginDAO;
import com.getir.readingisgood.document.Customer;
import com.getir.readingisgood.document.GrantedAuthorities;
import com.getir.readingisgood.exception.CustomerAlreadyExistsException;
import com.getir.readingisgood.helper.SequenceService;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.GrantedAuthoritiesRepository;
import com.getir.readingisgood.security.ApplicationUserRole;
import com.getir.readingisgood.service.CustomerAuthService;
import com.getir.readingisgood.service.enumaration.Sequence;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

@Service
public class CustomerAuthServiceImpl implements CustomerAuthService {

    @Value("${application.jwt.tokenPrefix}")
    private String tokenPrefix;

    private final SequenceService sequenceService;
    private final GrantedAuthoritiesRepository grantedAuthoritiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;

    public CustomerAuthServiceImpl(SequenceService sequenceService,
                                   GrantedAuthoritiesRepository grantedAuthoritiesRepository,
                                   PasswordEncoder passwordEncoder,
                                   CustomerRepository customerRepository,
                                   AuthenticationManager authenticationManager,
                                   SecretKey secretKey) {
        this.sequenceService = sequenceService;
        this.grantedAuthoritiesRepository = grantedAuthoritiesRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
    }

    @Override
    public String saveCustomer(CustomerCreateDAO customerDao) {
        Customer customer = null;
        synchronized (this) {
            boolean isCustomerExists = customerRepository.existsByEmail(customerDao.getEmail());
            if (isCustomerExists) {
                throw new CustomerAlreadyExistsException();
            }
            customer = new Customer(
                    sequenceService.getNextSequence(Sequence.CUSTOMER.getName()),
                    customerDao.getName(),
                    customerDao.getSurname(),
                    customerDao.getEmail());
            customerRepository.save(customer);
        }

        customer = customerRepository.findByEmail(customerDao.getEmail()).get();
        GrantedAuthorities byRole = grantedAuthoritiesRepository.findByRole(ApplicationUserRole.USER.getRole());
        customer.setGrantedAuthorities(byRole);
        customer.setPassword(passwordEncoder.encode(customerDao.getPassword()));
        customerRepository.save(customer);
        return loginCustomer(new CustomerLoginDAO(customer.getEmail(), customerDao.getPassword()));
    }

    @Override
    public String loginCustomer(CustomerLoginDAO login) {
        String token = "";
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate.isAuthenticated()) {
            token = Jwts.builder()
                    .setSubject(authenticate.getName())
                    .claim("authorities", authenticate.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                    .signWith(secretKey)
                    .compact();
        }

        return token.equals("") ? token : tokenPrefix + token;
    }
}
