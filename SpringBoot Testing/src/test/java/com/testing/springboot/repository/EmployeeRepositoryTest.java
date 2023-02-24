package com.testing.springboot.repository;

import com.testing.springboot.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder().
                firstName("john").lastName("michael")
                .email("john@gmail.com").build();
    }

    //junit test for save employee
    @DisplayName("Junit test for save employee")
    @Test
    public void saveEmployeeMethodTest(){
        Employee saveEmployee = employeeRepository.save(employee);
        Assertions.assertThat(saveEmployee).isNotNull();
        Assertions.assertThat(saveEmployee.getId()).isGreaterThan(0);
    }
    @DisplayName("Junit test for find all employee")
    @Test
    public void saveAllEmployeeMethodTest(){
        Employee employee1 = Employee.builder().
                firstName("mic").lastName("josh")
                .email("mic@gmail.com").build();
        Employee saveEmployee = employeeRepository.save(employee);
        Employee saveEmployee1 = employeeRepository.save(employee1);
        //when
        List<Employee> employeeList = employeeRepository.findAll();

        //then
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("Junit test for get employee by id")
    @Test
    public void getEmployeeByIdMethodTest(){
        Employee saveEmployee = employeeRepository.save(employee);
        //when
        Employee employeeDb = employeeRepository.findById(saveEmployee.getId()).get();
        //then
        Assertions.assertThat(employeeDb).isNotNull();

    }

    @DisplayName("Junit test for get employee by email")
    @Test
    public void getEmployeeByEmailMethodTest(){
        Employee saveEmployee = employeeRepository.save(employee);
        //when
        Employee employeeEmail = employeeRepository.findByEmail(saveEmployee.getEmail()).get();
        //then
        Assertions.assertThat(employeeEmail).isNotNull();

    }

    @DisplayName("Junit test for update employee")
    @Test
    public void updateEmployeeMethodTest(){
        Employee saveEmployee = employeeRepository.save(employee);
        //when
        saveEmployee.setEmail("josh@hotmail.com");
        Employee updated = employeeRepository.save(saveEmployee);
        //then
        Assertions.assertThat(updated.getEmail()).isEqualTo("josh@hotmail.com");

    }



}