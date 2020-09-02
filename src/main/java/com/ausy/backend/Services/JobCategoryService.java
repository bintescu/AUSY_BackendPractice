package com.ausy.backend.Services;

import com.ausy.backend.Exceptions.ErrorResponse;
import com.ausy.backend.Models.DAO.JobCategory;
import com.ausy.backend.Repositories.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoryService {


    @Autowired
    JobCategoryRepository jobCategoryRepository;

    public JobCategory addJobCategory(JobCategory jobCategory){

        if(jobCategory.isValid()) {
            return this.jobCategoryRepository.save(jobCategory);
        }
        else {
            throw new ErrorResponse("Job category is null",204);
        }
    }

    public JobCategory findJobCategory(int id){
        return jobCategoryRepository.findById(id);
    }
    public List<JobCategory> findAllJobCategories(){
        List<JobCategory> jobCategoryList =  jobCategoryRepository.findAll();
        if(jobCategoryList.size() == 0){
            throw new ErrorResponse("jobcategories table is null !",404);
        }
        else {
            return jobCategoryList;
        }
    }
    
    public void deleteJobCategory(int id){
        JobCategory jobCategory = jobCategoryRepository.findById(id);
        if(jobCategory == null){
            throw new ErrorResponse("Job category not found",404);
        }
        jobCategoryRepository.delete(jobCategory);
    }
}
