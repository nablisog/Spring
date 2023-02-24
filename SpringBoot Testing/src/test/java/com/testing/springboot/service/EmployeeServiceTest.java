package com.testing.springboot.service;

import com.testing.springboot.exception.ResourceNotFoundException;
import com.testing.springboot.model.Employee;
import com.testing.springboot.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

     private Employee employee;

    @Mock EmployeeRepository employeeRepository;

    @InjectMocks private EmployeeService employeeService;

    @BeforeEach
    public void setup(){
        employee = Employee.builder().id(1L)
                .firstName("john").lastName("Mic")
                .email("john@mail.com").build();

    }
    @DisplayName("Junit test for save employee method")
    @Test
    public void saveEmployeeMethodTest(){
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        Employee saveEmployee = employeeService.saveEmployee(employee);
        assertThat(saveEmployee).isNotNull();

    }
    @DisplayName("Junit test for save employee method throwing Exception")
    @Test
    public void saveEmployeeMethodThrowExceptionTest(){
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });

        verify(employeeRepository,never()).save(any(Employee.class));

    }

    @DisplayName("Junit test for getAllEmployee Method")
    @Test
    public void getAllEmployeeMethodTest() {
        Employee employee1 = Employee.builder().id(2L)
                .firstName("Josh").lastName("Mic")
                .email("jsh@mail.com").build();
        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        var employeeList = employeeService.getAllEmployee();
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);

    }
    @DisplayName("Junit test for getting employee by id")
    @Test
    public void getEmployeeByIdMethodTest(){
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        var savedEmployee = employeeService.getEmployeeById(employee.getId()).get();
        Assertions.assertThat(savedEmployee).isNotNull();

    }
    @DisplayName("Junit test for updating employee")
    @Test
    public void updateEmployeeMethodTest(){
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("updated@gmail.com");
        employee.setFirstName("updatedName");

        var updatedEmployee = employeeService.updateEmployee(employee);
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("updated@gmail.com");
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("updatedName");

    }

    @DisplayName("Junit test for deleting employee")
    @Test
    public void deleteEmployeeMethodTest(){
        long id = 1L;
        willDoNothing().given(employeeRepository).deleteById(id);
        employeeService.deleteEmployee(id);
        verify(employeeRepository,times(1)).deleteById(id);

    }
}