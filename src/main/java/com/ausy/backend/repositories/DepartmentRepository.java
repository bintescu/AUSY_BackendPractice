package com.ausy.backend.repositories;

import com.ausy.backend.models.DAO.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Department findById(int id);
}
