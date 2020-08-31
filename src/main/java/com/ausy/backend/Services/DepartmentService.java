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
            throw new ErrorResponse("Department added is null !",205);
        }
    }

    public List<Department> findAllDepartments(){
        List<Department> departmentList = departmentRepository.findAll();
        if(departmentList.size() == 0){
            throw  new ErrorResponse("Departments table is empty!",205);
        }
        else {
            return departmentList;
        }
    }

    public void removeDepartment(int id){
        Department department = null;
        try {
            department = departmentRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new ErrorResponse(e.getMessage(),404);
        }
        departmentRepository.delete(department);

    }
}
