package com.udacity.jdnd.course3.critter.services;


import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    /*
        get all pets
        get pet by id
        save new pet
        get pets by cust id
     */
    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private CustomerRepository customerRepository;


    /** return pet based on id
     * @return
     * */
    public Pet petById(Long petId) {
        return petsRepository.getOne(petId);
    }

    /**
     * get all pets
     * @return
     */
    public List<Pet> getAllPets(){
        return petsRepository.findAll();
    }

    /** get pets by customer id */
    public List<Pet> getPetsByCustomerId(Long customerId){
        List<Pet> pets = petsRepository.findAll().stream()
                .filter(pet -> pet.getCustomer().getId() == customerId)
                .collect(Collectors.toList());
        return pets;
    }

    /** save a pet based on customer *
     *
     */
    public Pet save(Pet pet, Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        pet.setCustomer(customer);
        pet = petsRepository.save(pet);
        customer.getPets().add(pet);
        customerRepository.save(customer);
        return pet;

    }
}
