package com.example.demo.services;

import com.example.demo.entities.Repair;
import com.example.demo.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RepairService {
    @Autowired
    private RepairRepository repairRepository;

    public ArrayList<Repair> findAll(){
        return repairRepository.findAll();
    }

    public Repair createRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    public Repair updateRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    public Repair findById(Long id){
        return repairRepository.findById(id).orElse(null);
    }

}
