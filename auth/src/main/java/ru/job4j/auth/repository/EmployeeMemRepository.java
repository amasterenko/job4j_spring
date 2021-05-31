package ru.job4j.auth.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class represents a memory storage for two predefined Employee objects.
 */

@Repository
public class EmployeeMemRepository {
    private final Map<Integer, Employee> employees;

    public EmployeeMemRepository(PersonRepository persons) {
        employees = new ConcurrentHashMap<>();
        Employee empl = new Employee(1, "Ivan", "Fedorov", 771234567);
        empl.addAccount(persons.findById(1).orElse(new Person()));
        empl.addAccount(persons.findById(2).orElse(new Person()));
        employees.put(1, empl);

        empl = new Employee(2, "Igor", "Kuznetsov", 785566787);
        empl.addAccount(persons.findById(3).orElse(new Person()));
        employees.put(2, empl);
    }

    public Collection<Employee> findAll() {
        return employees.values();
    }

    public Employee findById(int id) {
        return employees.get(id);
    }
}
