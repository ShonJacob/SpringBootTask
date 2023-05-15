package com.springboottest.crudapi.controller;

import com.springboottest.crudapi.entity.Department;
import com.springboottest.crudapi.entity.Employee;
import com.springboottest.crudapi.repository.DepartmentRepository;
import com.springboottest.crudapi.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ControllerClass {

    //@Autowired
    EmployeeRepository employeeRepository;

    //@Autowired
    DepartmentRepository departmentRepository;

    //Auto wiring using constructor, we can also use @Autowired on the EmployeeRespository and DepartmentRepository declaration
    public ControllerClass(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/api/employee/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id){
       return employeeRepository.findById(id);
    }

    @GetMapping("/api/employeePaged")
    public Page<Employee> getAllEmployeesPageable(){
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        return employeeRepository.findAll(firstPageWithTwoElements);
    }

    @GetMapping("/api/employee")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping("/api/createEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee createEmployee(@RequestBody Employee emp){
        System.out.println(emp);
        Optional<Department> existingDepartment = departmentRepository.findById(emp.getDepartment().getDept_id());
        if(existingDepartment.isPresent()){
            Department dept = existingDepartment.get();
            emp.setDepartment(dept);
            return employeeRepository.save(emp);
        }
        return null;
    }

    @PutMapping("/api/updateEmployee/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee emp){
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if(existingEmployee.isPresent()){
            Employee updatedEmp = existingEmployee.get();
            updatedEmp.setEmp_name(emp.getEmp_name());
            updatedEmp.setPhone(emp.getPhone());
            updatedEmp.setDepartment(emp.getDepartment());
            return employeeRepository.save(updatedEmp);
        }
        return null;
    }

    @DeleteMapping("/api/deleteEmployee/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployeeById(@PathVariable Long id){
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if(existingEmployee.isPresent()){
            employeeRepository.delete(existingEmployee.get());
            return "Employee "+id+" has been deleted!";
        }
        return "Employee not found!";
    }


    //-----------------------------------------------------

    @GetMapping("/api/department/{id}")
    public Optional<Department> getDepartmentById(@PathVariable Long id){
        return departmentRepository.findById(id);
    }

    @GetMapping("/api/department")
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    @PostMapping("/api/createDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    public Department createDepartment(@RequestBody Department dept){
        return departmentRepository.save(dept);
    }

    @PutMapping("/api/updateDepartment/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department dept){
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if(existingDepartment.isPresent()){
            Department updatedDept = existingDepartment.get();
            updatedDept.setDept_name(dept.getDept_name());
            updatedDept.setDept_location(dept.getDept_location());
            return departmentRepository.save(updatedDept);
        }
        return null;
    }

    @DeleteMapping("/api/deleteDepartment/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDepartmentById(@PathVariable Long id){
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if(existingDepartment.isPresent()){
            departmentRepository.delete(existingDepartment.get());
            return "Department "+id+" has been deleted!";
        }
        return "Department not found!";
    }

}
