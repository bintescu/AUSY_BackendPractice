package com.ausy.backend.Models.DAO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "department_name")
    private String name;

    @OneToMany(mappedBy = "department" , cascade = CascadeType.REMOVE)
    private List<Employee> employees ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public boolean isValid(){
        if(name == null){
            return false;
        }
        else if(name.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }
}
