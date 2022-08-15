package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    /** from DTO
     *  private long id;
     *     private List<Long> employeeIds;
     *     private List<Long> petIds;
     *     private LocalDate date;
     *     private Set<EmployeeSkill> activities;
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @ManyToMany(targetEntity = Pet.class)
    private List<Pet> petList;

    @ManyToMany(targetEntity = Employee.class)
    private List<Employee> employeeList;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    public Schedule() {
    }

    public Schedule(Long id, LocalDate date, List<Pet> petList, List<Employee> employeeList, Set<EmployeeSkill> activities) {
        this.id = id;
        this.date = date;
        this.petList = petList;
        this.employeeList = employeeList;
        this.activities = activities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
