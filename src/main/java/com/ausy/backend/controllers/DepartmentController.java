package com.ausy.backend.controllers;


import com.ausy.backend.exceptions.ErrorResponse;
import com.ausy.backend.models.DAO.Department;
import com.ausy.backend.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public ResponseEntity<Department> addDepartment(@RequestBody  Department department){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","saveDepartment");
        Department departmentAdded = null;
        try {
            departmentAdded = this.departmentService.addDepartment(department);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(departmentAdded);
    }

    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> departmentList = null;
        try{
            departmentList = departmentService.findAllDepartments();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","findAllDepartments");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(departmentList);
    }
    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity<Department> getAllDepartments(@PathVariable int id){
        Department department = null;
        try{
            department = departmentService.findDepById(id);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","findDepartmentById");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(department);
    }
    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","deleteDepartment");
        try {
            departmentService.removeDepartment(id);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("No value present");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Department deleted !");
    }
}
