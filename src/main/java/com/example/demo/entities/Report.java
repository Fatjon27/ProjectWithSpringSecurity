package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private Double price = 0.0 ;

    private Boolean completed = false;

    private Boolean refused = false;

    private Boolean paid = false;

    private Boolean collected = false;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private User technician;

    @ManyToOne
    @JoinColumn(name = "acceptance_id")
    private User acceptance;

    @ManyToOne
    @JoinColumn(name = "repaid_id")
    private Repair repair;
}

