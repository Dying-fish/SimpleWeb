package com.example.repository;

import com.example.domain.Illustration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IllustrationRepository extends CrudRepository<Illustration, Integer> {
    public Illustration findByPid(Integer pid);
    public ArrayList<Illustration> findAll();
}
