package com.example.demo.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repairs")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String deviceSerial;
    @NotBlank
    private String brand;
    @NotBlank
    private String deviceTemplate;
    @NotBlank
    private String problemDescription;
    private String purchaseDate;
    private String warrantyExpiryDate;
    @NotBlank
    private String additionalNotes;
    @NotBlank
    private String password;
    @NotBlank
    private String customerName;
    @NotBlank
    private String fullAddress;
    @NotBlank
    private String telephoneNumber;
    @NotBlank
    private String email;
    @NotBlank
    private String fiscalCode;
    @NotBlank
    private String vatNumber;
    @NotBlank
    private String pec;
    @NotBlank
    private String sdiCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "users_repairs",
//            joinColumns = @JoinColumn(name = "repair_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> users;

    @OneToMany(mappedBy = "repair")
    private List<Report> reports;

}
