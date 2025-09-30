package com.example.ems.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate creationDate;

    // Department Head is an Employee
    @OneToOne
    private Employee departmentHead;

    // One department can have many employees
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
