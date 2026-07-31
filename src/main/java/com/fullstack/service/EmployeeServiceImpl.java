package com.fullstack.service;

import com.fullstack.model.Employee;
import com.fullstack.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl {

    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findById(int empId){
        return employeeRepository.findById(empId);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee update(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteById(int empId){
        employeeRepository.deleteById(empId);
        System.out.println("Deleted Successfully !");
    }
}
