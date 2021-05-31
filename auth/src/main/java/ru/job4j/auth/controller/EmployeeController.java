package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeMemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Class serves requests for getting all of the employees,
 * adding, updating and deleting employees' accounts.
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    @Autowired
    private EmployeeMemRepository employees;

    @Autowired
    private RestTemplate rest;

    @GetMapping("/")
    public List<Employee> findAll() {
        return new ArrayList<>(employees.findAll());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Employee> create(@PathVariable int id, @RequestBody Person person) {
        Employee owner = employees.findById(id);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        Person rsl = rest.postForObject(API, person, Person.class);
        owner.addAccount(rsl);
        return new ResponseEntity<>(
                owner,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Person person) {
        Employee owner = employees.findById(id);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        rest.put(API, person);
        owner.updateAccount(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idEmpl}/{idAcc}")
    public ResponseEntity<Void> delete(@PathVariable int idEmpl, @PathVariable int idAcc) {
        Employee owner = employees.findById(idEmpl);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        Person account = new Person();
        account.setId(idAcc);
        rest.delete(API_ID, idAcc);
        owner.deleteAccount(account);
        return ResponseEntity.ok().build();
    }
}
