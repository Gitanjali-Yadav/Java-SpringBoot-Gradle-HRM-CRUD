package com.fullstack.controller;

import com.fullstack.exception.RecordNotFoundException;
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

    @PutMapping("/update/{empId}")
    public ResponseEntity<Employee> updateById(@PathVariable int empId, @RequestBody Employee employee){
        //first check if id exits before updating the record
        //we throw custom exception here
        Employee emp1=employeeservice.findById(empId).orElseThrow(()->new RecordNotFoundException("Employee ID does not exit! Cannot be updated !"));

        emp1.setEmpName(employee.getEmpName());
        emp1.setEmpSalary(employee.getEmpSalary());

        return ResponseEntity.ok(employeeservice.update(employee));

    }

    @DeleteMapping("/deleteById/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId){
        employeeservice.deleteById(empId);
        return ResponseEntity.ok("Data Deleted Succcessfully");
    }



}
