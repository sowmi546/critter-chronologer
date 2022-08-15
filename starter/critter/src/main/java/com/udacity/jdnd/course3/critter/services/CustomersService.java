package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetsRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomersService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private PetService petService;

    /**
     * return all customers
     * @return
     */
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    /**
     * return customer based on Id
     * @param customerId
     * @return
     */
    public Customer getCustomerById(Long customerId){
        return customerRepository.getOne(customerId);
    }

    /**
     * get all pets by CustomerID
     * @param customerId
     * @return
     */
    public List<Pet> getAllPetsByCustomerId(Long customerId){
      //  return petsRepository.getPetsByCustomer(customerId);
        return petService.getPetsByCustomerId(customerId);
    }

    /**
     * get customer based on pet id
     * @param petId
     * @return
     */
    public Customer getCustomerByPetId(Long petId){
        return petsRepository.getOne(petId).getCustomer();

    }


    /**
     * save customer with all petIds
     * @param customer
     * @param petIds
     * @return
     */
    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> pets = new ArrayList<>();
        if(petIds != null && !petIds.isEmpty()) {
            pets = petIds.stream()
                    .map(petId -> petsRepository.getOne(petId))
                    .collect(Collectors.toList());
        }
        customer.setPets(pets);
        return customerRepository.save(customer);
    }






}

