package com.getir.readingisgood;

import com.getir.readingisgood.document.Customer;
import com.getir.readingisgood.document.GrantedAuthorities;
import com.getir.readingisgood.helper.SequenceService;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.GrantedAuthoritiesRepository;
import com.getir.readingisgood.service.enumaration.Sequence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class ReadingIsGoodApplication {

    private final GrantedAuthoritiesRepository grantedAuthoritiesRepository;
    private final CustomerRepository customerRepository;
    private final SequenceService sequenceService;

    public ReadingIsGoodApplication(GrantedAuthoritiesRepository grantedAuthoritiesRepository,
                                    CustomerRepository customerRepository,
                                    SequenceService sequenceService) {
        this.grantedAuthoritiesRepository = grantedAuthoritiesRepository;
        this.customerRepository = customerRepository;
        this.sequenceService = sequenceService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReadingIsGoodApplication.class, args);
    }


    @PostConstruct
    public void run() {
        GrantedAuthorities grantUSER = new GrantedAuthorities(1L, "ROLE_USER");
        GrantedAuthorities grantMANAGER = new GrantedAuthorities(2L, "ROLE_MANAGER");
        grantedAuthoritiesRepository.saveAll(Arrays.asList(grantUSER, grantMANAGER));

        Customer customer = new Customer(1L,
                "admin",
                "admin",
                "admin",
                "$2a$10$WzXsA506to2HiO6MZOVipOYHo34LFVCjJpgZ7qF1KXXdkiATeKHZu",
                grantMANAGER);

        sequenceService.getNextSequence(Sequence.CUSTOMER.getName());
        customerRepository.save(customer);
    }
}
