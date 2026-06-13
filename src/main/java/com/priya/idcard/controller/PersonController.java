package com.priya.idcard.controller;

import com.priya.idcard.model.Person;
import com.priya.idcard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/idcard")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public   List<Person> getAllPerson(){

        return personRepository.findAll();
    }

    @PostMapping
    public  Person createPerson(@RequestBody Person person){


        return personRepository.save(person);
    }


    @PutMapping("/{id}")

    public Person updatePerson(@PathVariable Long id,@RequestBody Person updatedPerson){
        Person person=personRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Person Not Found"));

        person.setName(updatedPerson.getName());
        person.setDepartment(updatedPerson.getDepartment());
        person.setCity(updatedPerson.getCity());

        return personRepository.save(person);



    }
    @DeleteMapping("/{id}")
    public String  deletePerson(@PathVariable Long id){
        personRepository.deleteById(id);
        return "deleted successfully";

    }
}
