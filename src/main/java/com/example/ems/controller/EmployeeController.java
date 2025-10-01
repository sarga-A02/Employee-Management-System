package com.example.ems.controller;

import com.example.ems.entity.Employee;
import com.example.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Object getAllEmployees(@RequestParam(required = false) Boolean lookup) {
        if (lookup != null && lookup) {
            return employeeService.getAllEmployees()
                    .stream()
                    .map(emp -> Map.of("id", emp.getId(), "name", emp.getName()))
                    .toList();
        }
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/id-and-department/{id}")
    public Map<String, Object> getEmployeeIdAndDepartment(@PathVariable Long id) {
        Employee emp = employeeService.getEmployeeById(id);
        return Map.of(
                "id", emp.getId(),
                "department", emp.getDepartment()
        );
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PostMapping("/bulk")
    public List<Employee> createEmployees(@RequestBody List<Employee> employees) {
        return employeeService.saveAllEmployees(employees);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee with ID " + id + " deleted!";
    }
}
