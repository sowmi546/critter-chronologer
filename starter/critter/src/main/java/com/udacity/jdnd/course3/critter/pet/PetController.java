package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomersService;
import com.udacity.jdnd.course3.critter.services.PetService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomersService customersService;

    private PetDTO convertPetTOPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        Pet p = petService.save(pet, petDTO.getOwnerId());
        List<Long> petIds = new ArrayList<>();
        petIds.add(p.getId());
        customersService.saveCustomer(customersService.getCustomerById(petDTO.getOwnerId()), petIds);
        return convertPetTOPetDTO(p);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.petById(petId);
        return convertPetTOPetDTO(pet);

    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream()
                .map(this::convertPetTOPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
       List<Pet> petsByOwner = petService.getPetsByCustomerId(ownerId);
       if(petsByOwner !=null&& !petsByOwner.isEmpty()){
           return petsByOwner.stream()
                   .map(this::convertPetTOPetDTO)
                   .collect(Collectors.toList());
       }
       else
       {
           List<PetDTO> emptyDTO = new ArrayList<>();
           return emptyDTO;
       }

    }
}
