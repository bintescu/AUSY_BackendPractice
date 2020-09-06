package com.ausy.backend.repositories;


import com.ausy.backend.models.DAO.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findById (int id);

    List<Employee> findAllByOrderBySalary();

    @Query(value = "SELECT * FROM employees e where e.department_id = :departmentid order by salary",nativeQuery = true)
    List<Employee> findAllByDepartmentdOrderBySalary(@Param("departmentid") int department);

}
