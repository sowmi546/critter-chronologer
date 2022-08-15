package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetsRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private PetService petService;

    /**
     * retrieve all schedules
     */

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();

    }

    /**
     * add a new schedule
     */

    public Schedule addNewSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds){
        //get list of all employees & pets , set them to a specific schedule
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petsRepository.findAllById(petIds);
        schedule.setEmployeeList(employees);
        schedule.setPetList(pets);
        return scheduleRepository.save(schedule);
    }
    /** schedules based on employee, customer and pet
     *
     */
    public List<Schedule> getSchedulesForCustomer(Long customerId){
        List<Pet> customerPets = petService.getPetsByCustomerId(customerId);
        List<List<Schedule>> customerSchedules = customerPets.stream()
                .map(pet -> this.getSchedulesForPet(pet.getId()))
                //.flatMap(Collection::stream)
                .collect(Collectors.toList());
        return customerSchedules.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<Schedule> getSchedulesForPet(Long petId){
        Pet p = petsRepository.getOne(petId);
       List<Schedule> petSchedules = scheduleRepository.findAll()
               .stream()
               .filter(schedule -> schedule.getPetList()
                       .contains(p)).collect(Collectors.toList());
       return petSchedules;
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        List<Schedule> userSchedules= scheduleRepository.findAll()
                        .stream().
                        filter(schedule -> schedule.getEmployeeList()
                                .contains(employee)).collect(Collectors.toList());

        return userSchedules;


    }








}
