package com.example.repository;

import com.example.domain.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<Authority, Integer> {
    Authority findByAuthority(String role);
}
