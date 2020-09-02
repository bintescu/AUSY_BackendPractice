package com.ausy.backend.Controllers;


import com.ausy.backend.Exceptions.ErrorResponse;
import com.ausy.backend.Mapper.EmployeeMapper;
import com.ausy.backend.Models.DAO.Employee;
import com.ausy.backend.Models.DTO.EmployeeDTO;
import com.ausy.backend.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;


    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployee()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getAllEmployees");
        List<Employee> employeeList = null;
        try {
             employeeList = employeeService.findAll();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);

    }

    @PostMapping("addEmployee/{department}/{jobcategory}")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee , @PathVariable int department, @PathVariable int jobcategory){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","addEmployeeDepJob");
        Employee employeeResp = null;
        try {
            employeeResp = employeeService.addEmployee(employee,department,jobcategory);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(employeeResp);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(employeeResp);

    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","deleteEmployee");
        try {
            employeeService.deleteEmployee(id);
        }
        catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Deleted.");
    }

    @GetMapping("/getEmployeeDTO/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeDTO(@PathVariable int id){
        EmployeeDTO employeeDTO = null;
        Employee employee = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeeDTO");
        try {
            employee = employeeService.findEmployeeById(id);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTO = employeeMapper.convertEmployeeToDto(employee);
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeDTO);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id){
        Employee employee = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployee");
        try {
            employee = employeeService.findEmployeeById(id);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employee);
    }
    @PutMapping("/updateEmployee/{employeeId}/{departmentId}/{jobCategoryId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,@PathVariable int employeeId, @PathVariable int departmentId, @PathVariable int jobCategoryId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","updateEmployee");
        Employee employeeUpdated;
        try {
            employeeUpdated = employeeService.updateEmployee(employee,employeeId,departmentId,jobCategoryId);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(employeeUpdated);
    }

    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesDTO(){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve","getAllEmployees");

        try {
            employeeList = employeeService.findAll();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }


    @GetMapping("/getEmployeesByDepartment/{departmentid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDep(@PathVariable int departmentid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByDep");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeeByDepartment(departmentid);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @GetMapping("/getEmployeesByJob/{jobid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByJob(@PathVariable int jobid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByJob");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeeByJobId(jobid);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @GetMapping("/getEmployeesByDepartmentAndJob/{departmentid}/{jobid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDepandJob(@PathVariable int departmentid,@PathVariable int jobid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByDepartmentAndJobId");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeesByDepAndJob(jobid,departmentid);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @GetMapping("/getEmployeesOrderBySalary")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOrderBySalary(){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve","getEmployeesOrderBySalary");

        try {
            employeeList = employeeService.findEmployeesOrderBySalary();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }





}
