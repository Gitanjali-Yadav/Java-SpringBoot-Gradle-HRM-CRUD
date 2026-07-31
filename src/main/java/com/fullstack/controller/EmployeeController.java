package com.fullstack.controller;

import com.fullstack.model.Employee;
import com.fullstack.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeservice;

    @PostMapping("/save")
    public ResponseEntity<Employee> save(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeservice.save(employee));
    }

    @GetMapping("/getEmpByID/{empId}")
    public ResponseEntity<Optional<Employee>> findById (@PathVariable int empId){
        return ResponseEntity.ok(employeeservice.findById(empId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> findAll(){
        return ResponseEntity.ok(employeeservice.findAll());
    }

    

}
