package com.testing.springboot.service;

import com.testing.springboot.exception.ResourceNotFoundException;
import com.testing.springboot.model.Employee;
import com.testing.springboot.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        var savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()){
            throw new ResourceNotFoundException("Employee already exist: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(long id){
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Employee updated){
        return employeeRepository.save(updated);
    }
    public void deleteEmployee(long id){
        employeeRepository.deleteById(id);
    }


}
