package com.ausy.backend.Repositories;

import com.ausy.backend.Models.DAO.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory,Integer> {
}
