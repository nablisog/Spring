package com.testing.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.springboot.model.Employee;
import com.testing.springboot.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
class EmployeeControllerTest {
    private Employee employee;
    @Autowired private MockMvc mockMvc;
    @MockBean private EmployeeService employeeService;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().id(1L)
                .firstName("Johnny").lastName("Mic")
                .email("john@mail.com").build();
    }
    @DisplayName("Junit test for createEmployee Method")
    @Test
    public void createEmployeeMethodTest() throws Exception {

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Johnny"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Mic"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@mail.com"));


    }
    @DisplayName("Junit test for getAllEmployee Method")
    @Test
    public void getAllEmployeesMethodTest() throws Exception{
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("Josh").lastName("Joshua").email("j@jamil.com").build());
        employeeList.add(Employee.builder().firstName("NANI").lastName("NANA").email("NA@email.com").build());

        BDDMockito.given(employeeService.getAllEmployee()).willReturn(employeeList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeList)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(employeeList.size())));


    }
    @DisplayName("Junit test for getEmployeeById Method")
    @Test
    public void getEmployeeByIdMethodTest() throws Exception {
        BDDMockito.given(employeeService.getEmployeeById(employee.getId()))
                .willReturn(Optional.of(employee));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                        .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employee.getEmail()));


    }
    @DisplayName("Junit test for invalid getEmployeeById Method")
    @Test
    public void invalidGetEmployeeByIdMethodTest() throws Exception {
        BDDMockito.given(employeeService.getEmployeeById(employee.getId()))
                .willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andDo(MockMvcResultHandlers.print());
    }
    @DisplayName("Junit test for  updateEmployee Method")
    @Test
    public void updateEmployeeMethodTest() throws Exception {

        BDDMockito.given(employeeService.getEmployeeById(employee.getId()))
                .willReturn(Optional.of(employee));
        employee.setFirstName("updatedName");
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(employee.getFirstName()));

    }
    @DisplayName("Junit test for  invalid updateEmployee Method")
    @Test
    public void invalidUpdateEmployeeMethodTest() throws Exception {

        BDDMockito.given(employeeService.getEmployeeById(employee.getId()))
                .willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));


    }

    @DisplayName("Junit test for deleteEmployee Method")
    @Test
    public void deleteEmployee() throws Exception{

        BDDMockito.willDoNothing().given(employeeService).deleteEmployee(employee.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}