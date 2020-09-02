package com.ausy.backend.Mapper;

import com.ausy.backend.Exceptions.ErrorResponse;
import com.ausy.backend.Models.DAO.Employee;
import com.ausy.backend.Models.DTO.EmployeeDTO;
import com.ausy.backend.Repositories.DepartmentRepository;
import com.ausy.backend.Repositories.EmployeeRepository;
import com.ausy.backend.Repositories.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobCategoryRepository jobCategoryRepository;

    public EmployeeDTO convertEmployeeToDto(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        if(employee != null) {
            employeeDTO.setFirstname(employee.getFirstName());
            employeeDTO.setLastname(employee.getLastName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setTelephone(employee.getTelephone());
            employeeDTO.setId(employee.getId());
            employeeDTO.setActive(employee.isActive());
            employeeDTO.setEndDate(employee.getEndDate());
            employeeDTO.setStartDate(employee.getStartDate());
            employeeDTO.setManager(employee.isManager());
            try {
                employeeDTO.setDepartment(employee.getDepartment().getName());
            }catch (NullPointerException e){
                ErrorResponse.LogError(new ErrorResponse(e,"Null department.",206));
                employeeDTO.setDepartment(null);
            }
            try {
                employeeDTO.setJobCategory(employee.getJobCategory().getName());
            }catch (NullPointerException e){
                ErrorResponse.LogError(new ErrorResponse(e,"Null jobcategory.",206));
                employeeDTO.setJobCategory(null);
            }
        }
        else {
            throw new ErrorResponse("Can not convert a null to EmployeeDTO",400);
        }
        return employeeDTO;
    }
}
