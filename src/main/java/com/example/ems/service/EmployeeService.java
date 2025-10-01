package com.example.ems.service;

import com.example.ems.entity.Employee;
import com.example.ems.entity.Department;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    // Get single employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Create or update single employee
    public Employee saveEmployee(Employee employee) {
        // Set department if provided
        if (employee.getDepartment() != null) {
            Long deptId = employee.getDepartment().getId();
            Department dept = departmentService.getDepartmentById(deptId);
            employee.setDepartment(dept);
        }

        // Set reporting manager if provided
        if (employee.getReportingManager() != null) {
            Long managerId = employee.getReportingManager().getId();
            Employee manager = getEmployeeById(managerId);
            employee.setReportingManager(manager);
        }

        return employeeRepository.save(employee);
    }

    // Bulk create employees
    public List<Employee> saveAllEmployees(List<Employee> employees) {
        for (Employee emp : employees) {
            saveEmployee(emp);
        }
        return employees;
    }

    // Update employee
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee emp = getEmployeeById(id);
        emp.setName(employeeDetails.getName());
        emp.setDob(employeeDetails.getDob());
        emp.setSalary(employeeDetails.getSalary());
        emp.setAddress(employeeDetails.getAddress());
        emp.setRole(employeeDetails.getRole());
        emp.setJoiningDate(employeeDetails.getJoiningDate());
        emp.setYearlyBonusPercentage(employeeDetails.getYearlyBonusPercentage());

        // Update department
        if (employeeDetails.getDepartment() != null) {
            Long deptId = employeeDetails.getDepartment().getId();
            Department dept = departmentService.getDepartmentById(deptId);
            emp.setDepartment(dept);
        } else {
            emp.setDepartment(null);
        }

        // Update reporting manager
        if (employeeDetails.getReportingManager() != null) {
            Long managerId = employeeDetails.getReportingManager().getId();
            Employee manager = getEmployeeById(managerId);
            emp.setReportingManager(manager);
        } else {
            emp.setReportingManager(null);
        }

        return employeeRepository.save(emp);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
