package com.ausy.backend.Services;

import com.ausy.backend.Exceptions.ErrorResponse;
import com.ausy.backend.Models.DAO.Department;
import com.ausy.backend.Repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

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
        Department department = null;
        try {
            department = departmentRepository.findById(id);
        }catch (RuntimeException e){
            throw new ErrorResponse(e.getMessage(),404);
        }
        departmentRepository.delete(department);

    }
}
