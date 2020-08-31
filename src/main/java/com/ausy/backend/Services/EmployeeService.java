package com.ausy.backend.Services;

import com.ausy.backend.Models.DAO.Employee;
import com.ausy.backend.Repositories.DepartmentRepository;
import com.ausy.backend.Repositories.EmployeeRepository;
import com.ausy.backend.Repositories.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobCategoryRepository jobCategoryRepository;

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }


}
