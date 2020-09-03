package com.ausy.backend.controllers;


import com.ausy.backend.Exceptions.ErrorResponse;
import com.ausy.backend.Models.DAO.JobCategory;
import com.ausy.backend.Services.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobcategories")
@CrossOrigin(origins = "*")
public class JobCategoryController {

    @Autowired
    JobCategoryService jobCategoryService;

    @PostMapping("/addJobCategory")
    public ResponseEntity<JobCategory> addJobCategory(@RequestBody JobCategory jobCategory){
        JobCategory jobCategoryAdded ;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","addJobCategory");
        try{
            jobCategoryAdded =jobCategoryService.addJobCategory(jobCategory);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(jobCategory);
    }

    @GetMapping("/getAllJobCategories")
    public ResponseEntity<List<JobCategory>> getAllJobCategories(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getAllJobCategories");
        List<JobCategory> jobCategoryList;
        try{
            jobCategoryList = jobCategoryService.findAllJobCategories();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(jobCategoryList);
    }

    @GetMapping("/getJobCategoryById/{id}")
    public ResponseEntity<JobCategory> getJobCategory(@PathVariable int id){
        JobCategory jobCategory = jobCategoryService.findJobCategory(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getJobCategoryById");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(jobCategory);
    }

    @DeleteMapping("/deleteJobCategory/{id}")
    public ResponseEntity<String> deleteJobCategory(@PathVariable int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","deleteJobCategory");
        try{
            jobCategoryService.deleteJobCategory(id);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Job Category not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Job category deleted !");
    }
}
