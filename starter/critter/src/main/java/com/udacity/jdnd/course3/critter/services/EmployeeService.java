package com.udacity.jdnd.course3.critter.services;


import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /** return all employees
     *
     * @param
     * @return
     */
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    /**
     * get employee details by ID
     * @param employeeId
     * @return
     */
    public Employee getEmployeeById(Long employeeId){
        return employeeRepository.getOne(employeeId);
    }

    /**
     * save a new employee
     * @param employee
     * @return
     */
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    /**
     * set availability of employee based on id
     */

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    /**
     * get list of employees availability based on skill for a date
     */
    public List<Employee> getAvailabilityBySkill(LocalDate date, Set<EmployeeSkill> skills){
        List<Employee> employees = employeeRepository.findAll().stream().filter(employee -> employee.getDaysAvailable().contains(date.getDayOfWeek())).collect(Collectors.toList());

        return employees.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }










}
