package ru.job4j.auth.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int inn;
    private Date employmentDate = new Date(System.currentTimeMillis());
    private List<Person> accounts = new ArrayList<>();

    public Employee(int id, String firstName, String lastName, int inn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.inn = inn;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public List<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Person> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Person person) {
        accounts.add(person);
    }

    public boolean updateAccount(Person person) {
        int index = accounts.indexOf(person);
        if (index == -1) {
            return false;
        }
        accounts.set(index, person);
        return true;
    }

    public boolean deleteAccount(Person person) {
        int index = accounts.indexOf(person);
        if (index == -1) {
            return false;
        }
        accounts.remove(index);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
