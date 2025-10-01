package com.example.ems.controller;

import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.service.DepartmentService;
import com.example.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        if (department.getCreationDate() == null) {
            department.setCreationDate(LocalDate.now());
        }
        return departmentService.saveDepartment(department);
    }

    @PostMapping("/bulk")
    public List<Department> createDepartments(@RequestBody List<Department> departments) {
        for (Department dep : departments) {
            if (dep.getCreationDate() == null) {
                dep.setCreationDate(LocalDate.now());
            }
        }
        return departmentService.saveAllDepartments(departments);
    }

    @GetMapping
    public Object getAllDepartments(@RequestParam(required = false) String expand) {
        List<Department> departments = departmentService.getAllDepartments();

        if ("employee".equalsIgnoreCase(expand)) {
            return departments.stream().map(dep -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", dep.getId());
                map.put("name", dep.getName());
                map.put("creationDate", dep.getCreationDate());
                map.put("departmentHead", dep.getDepartmentHead());

                List<Employee> employees = employeeService.getAllEmployees()
                        .stream()
                        .filter(emp -> emp.getDepartment() != null && emp.getDepartment().getId().equals(dep.getId()))
                        .toList();
                map.put("employees", employees);

                return map;
            }).toList();
        }

        return departments;
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        return departmentService.updateDepartment(id, departmentDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        List<Employee> employeesInDept = employeeService.getAllEmployees()
                .stream()
                .filter(emp -> emp.getDepartment() != null && emp.getDepartment().getId().equals(id))
                .toList();

        if (!employeesInDept.isEmpty()) {
            throw new RuntimeException("Cannot delete department. Employees are assigned to this department.");
        }

        departmentService.deleteDepartment(id);
        return "Department with id " + id + " deleted successfully!";
    }
}
