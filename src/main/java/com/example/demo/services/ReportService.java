package com.example.demo.services;

import com.example.demo.entities.Report;
import com.example.demo.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public ArrayList<Report> findAll(){
        return reportRepository.findAll();
    }

    public Report createReport(Report report){
        return reportRepository.save(report) ;
    }

    public Report updateReport(Report report){
        return reportRepository.save(report) ;
    }

    public Report findById(Long id){
        return reportRepository.findById(id).orElse(null);
    }


}