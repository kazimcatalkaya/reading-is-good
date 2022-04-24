package com.getir.readingisgood.repository;

import com.getir.readingisgood.document.GrantedAuthorities;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GrantedAuthoritiesRepository extends MongoRepository<GrantedAuthorities, Long> {

    GrantedAuthorities findByRole(String role);
}
