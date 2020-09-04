package com.ausy.backend.services;

import com.ausy.backend.exceptions.ErrorResponse;
import com.ausy.backend.models.DAO.Department;
import com.ausy.backend.models.DAO.Employee;
import com.ausy.backend.models.DAO.JobCategory;
import com.ausy.backend.repositories.DepartmentRepository;
import com.ausy.backend.repositories.EmployeeRepository;
import com.ausy.backend.repositories.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobCategoryRepository jobCategoryRepository;

    public List<Employee> findAll(){
        List<Employee> employeeList = employeeRepository.findAll();
            return employeeList;
    }

    public Employee addEmployee(Employee employee, int departmentid, int jobcategoryid){
        Department department =null;
        JobCategory jobCategory = null;
        department = departmentRepository.findById(departmentid);
        jobCategory = jobCategoryRepository.findById(jobcategoryid);
        if(department == null){
            throw new ErrorResponse("No value present for this department id" ,404);
        }
        if(jobCategory == null){
            throw new ErrorResponse( "No value present for this jobcategory id" ,404);

        }

        employee.setDepartment(department);
        employee.setJobCategory(jobCategory);
        return employeeRepository.save(employee);

    }

    public List<Employee> setManager(List<Integer> employees, int managerId){
        List<Employee> employeeList = new ArrayList<>();
        Employee manager;

        try {
            employees.stream().forEach(e ->employeeList.add(employeeRepository.findById(e).get()));
        }catch (NoSuchElementException e){
            throw new ErrorResponse("Bad id of employee in list of employees",404);
        }
        try {
            manager = employeeRepository.findById(managerId);
        }
        catch (NoSuchElementException e){
            throw new ErrorResponse("Bad id for manager",404);
        }

        if(!manager.isManager()){
           throw new ErrorResponse("The manager_id does not refer to a manager.",404);
        }
        if(employeeList.stream().anyMatch(e -> e.getId() == managerId)){
            throw new ErrorResponse("An employee can not be his manager",400);
        }
        employeeList.forEach(e -> e.setManagerId(manager));
        employeeList.forEach(e -> employeeRepository.save(e));


        return employeeList;
    }

    public boolean deleteEmployee(int id){
        Employee employee = employeeRepository.findById(id);
            if(employee == null){
                throw new ErrorResponse("No employee present for this id",404);
            }

            if(employee.isManager()){
                List<Employee> employeeList = findAll();
                employeeList.stream().filter(e->e.getManagerId() != null).filter(e->e.getManagerId().getId() == id).forEach(e -> e.setManagerId(null));
            }
        employeeRepository.delete(employee);
        return true;
    }

    public Employee findEmployeeById(int id){
        Employee employee =  null;

            employee = employeeRepository.findById(id);
        if(employee == null){
            throw new ErrorResponse("No employee present for this id",404);
        }
        return employee;
    }

    public Employee updateEmployee(Employee employee, int employeeId, int departmentId, int jobCategoryId){
        Employee updatedEmployee = findEmployeeById(employeeId);

        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());


        Department department = departmentRepository.findById(departmentId);
        JobCategory jobCategory = jobCategoryRepository.findById(jobCategoryId);
        if(department == null || jobCategory == null){
            throw new ErrorResponse("Department or Jobcategory is null",404);
        }

        updatedEmployee.setDepartment(department);
        updatedEmployee.setJobCategory(jobCategory);
        updatedEmployee.setActive(employee.isActive());
        updatedEmployee.setAddress(employee.getAddress());
        updatedEmployee.setBirthday(employee.getBirthday());
        updatedEmployee.setCP(employee.getCP());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setStartDate(employee.getStartDate());
        updatedEmployee.setEndDate(employee.getEndDate());
        updatedEmployee.setHasDrivingLicense(employee.isHasDrivingLicense());
        updatedEmployee.setManager(employee.isManager());
        updatedEmployee.setNoChildren(employee.getNoChildren());
        updatedEmployee.setSalary(employee.getSalary());
        updatedEmployee.setSocialSecurityNumber(employee.getSocialSecurityNumber());
        updatedEmployee.setStudies(employee.getStudies());
        updatedEmployee.setTelephone(employee.getTelephone());

        employeeRepository.save(updatedEmployee);
        return updatedEmployee;

    }
    public List<Employee> findEmployeeByDepartment(int departmentId){
        List<Employee> employeeList = findAll();
       return employeeList.stream().filter(e -> e.getDepartment() != null).filter(e -> e.getDepartment().getId() == departmentId).collect(Collectors.toList());
    }

    public List<Employee> findEmployeeByJobId(int jobid){
        List<Employee> employeeList = findAll();
        return employeeList.stream().filter(e -> e.getJobCategory() != null).filter(e -> e.getJobCategory().getId() == jobid).collect(Collectors.toList());
    }

    public List<Employee> findEmployeesByDepAndJob(int jobid, int depid){
        List<Employee> employeeList = findAll();
        return employeeList.stream().filter(e -> e.getDepartment() != null).filter(e -> e.getDepartment().getId() == depid).filter(e -> e.getJobCategory() != null).filter(e -> e.getJobCategory().getId() == jobid).collect(Collectors.toList());
    }


    public List<Employee> findEmployeesOrderBySalary(){
        return employeeRepository.findAllByOrderBySalary();
    }

    public List<Employee> findEmployeesByDepartmentOrderBySalary(int department){
        return employeeRepository.findAllByDepartmentdOrderBySalary(department);
    }

    public List<Employee> findEmployeesByManager(int managerId){
        List<Employee> employeeList = findAll();
        return employeeList.stream().filter(e -> e.getManagerId() != null).filter(e -> e.getManagerId().getId() == managerId).collect(Collectors.toList());
    }

    public void setProfilePicture(int employeeid, byte[] image){
        Employee employee = employeeRepository.findById(employeeid);
        if(employee == null){
            throw new ErrorResponse("Employee not found!",404);
        }
        else {
                employee.setProfilePicture(image);
                employeeRepository.save(employee);
        }

    }

}
