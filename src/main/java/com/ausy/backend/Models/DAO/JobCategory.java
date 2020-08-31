package com.ausy.backend.Models.DAO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobcategories")
public class JobCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "jobcategory_name")
    private String name;

    @OneToMany(mappedBy = "jobCategory", cascade = CascadeType.REMOVE)
    private List<Employee> employees;

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

    @Override
    public String toString() {
        return "JobCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                '}';
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
