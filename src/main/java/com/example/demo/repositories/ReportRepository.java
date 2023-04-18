package com.example.demo.repositories;

import com.example.demo.entities.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReportRepository extends CrudRepository<Report,Long> {
    public ArrayList<Report> findAll();

}

