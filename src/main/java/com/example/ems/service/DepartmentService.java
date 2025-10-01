package com.example.ems.service;

import com.example.ems.entity.Department;
import com.example.ems.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Create or update a single department
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Bulk create departments
    public List<Department> saveAllDepartments(List<Department> departments) {
        return departmentRepository.saveAll(departments);
    }

    // Get department by ID
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Update department
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department dep = getDepartmentById(id);
        dep.setName(departmentDetails.getName());
        dep.setDepartmentHead(departmentDetails.getDepartmentHead());
        dep.setCreationDate(departmentDetails.getCreationDate());
        return departmentRepository.save(dep);
    }

    // Delete department
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
