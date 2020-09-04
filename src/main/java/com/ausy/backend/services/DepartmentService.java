package com.ausy.backend.services;

import com.ausy.backend.exceptions.ErrorResponse;
import com.ausy.backend.models.DAO.Department;
import com.ausy.backend.models.DAO.Employee;
import com.ausy.backend.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeService employeeService;
    public Department addDepartment(Department department){
        if (department.isValid()) {
            return departmentRepository.save(department);
        }
        else
        {
            throw new ErrorResponse("Department added is null !",400);
        }
    }

    public List<Department> findAllDepartments(){
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;

    }

    public Department findDepById(int id){
        return departmentRepository.findById(id);
    }

    public void removeDepartment(int id){
        Department department = departmentRepository.findById(id);

       if(department == null){
            throw new ErrorResponse("Department not found",404);
        }
        List<Employee> employeeDeletedWithDepartment = employeeService.findEmployeeByDepartment(id);

        List<Employee> employeeList = employeeService.findAll();
        for (Employee emp : employeeDeletedWithDepartment) {
            employeeList.stream().filter(e -> e.getManagerId() != null).filter(e -> e.getManagerId().getId() == emp.getId()).forEach(e -> e.setManagerId(null));
        }
        departmentRepository.delete(department);



    }
}
