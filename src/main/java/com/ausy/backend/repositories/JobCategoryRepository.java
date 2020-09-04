package com.ausy.backend.repositories;

import com.ausy.backend.models.DAO.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory,Integer> {
    JobCategory findById(int id);
}
