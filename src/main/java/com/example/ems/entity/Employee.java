package com.example.ems.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate dob;
    private double salary;
    private String address;
    private String role;
    private LocalDate joiningDate;
    private double yearlyBonusPercentage;

    @ManyToOne(optional = true)
    private Department department;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = true)
    private Employee reportingManager;
}
