package com.example.demo.repositories;

import com.example.demo.entities.Repair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RepairRepository extends CrudRepository<Repair,Long> {
    public ArrayList<Repair> findAll();
}
