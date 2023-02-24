package com.testing.springboot.controller;

import com.testing.springboot.model.Employee;
import com.testing.springboot.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@EnableWebMvc
@Controller
@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee>getEmployeeById(@PathVariable("id") long id){
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity ::ok).orElseGet(()->ResponseEntity.notFound().build());

    }

    @PutMapping("{id}")
    public ResponseEntity<Employee>updateEmployee(@PathVariable("id")long id,@RequestBody Employee employee){
        return employeeService.getEmployeeById(id)
                .map(saveEmployee -> {
                    saveEmployee.setFirstName(employee.getFirstName());
                    saveEmployee.setLastName(employee.getLastName());
                    saveEmployee.setEmail(employee.getEmail());
                    var updated = employeeService.updateEmployee(saveEmployee);
                    return new ResponseEntity<>(updated, HttpStatus.OK);
                })
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteEmployee(@PathVariable("id")long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }


}
