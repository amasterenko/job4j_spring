package ru.job4j.auth.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {
    @MockBean
    private PersonRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetByIdThenReturnJson() throws Exception {
        Person person = new Person(1, "Person", "Password");
        when(repository.findById(1)).thenReturn(Optional.of(person));
        this.mockMvc.perform(get("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.id", is(person.getId())))
                .andExpect(jsonPath("$.login", is(person.getLogin())))
                .andExpect(jsonPath("$.password", is(person.getPassword())));
    }

    @Test
    public void whenGetByIdThenNotFound() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetAllThenReturnJsonArray() throws Exception {
        Person person1 = new Person(1, "Person1", "Password1");
        Person person2 = new Person(2, "Person2", "Password2");
        when(repository.findAll()).thenReturn(List.of(person1, person2));
        this.mockMvc.perform(get("/person/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(person1.getId())))
                .andExpect(jsonPath("$[0].login", is(person1.getLogin())))
                .andExpect(jsonPath("$[0].password", is(person1.getPassword())))
                .andExpect(jsonPath("$[1].id", is(person2.getId())))
                .andExpect(jsonPath("$[1].login", is(person2.getLogin())))
                .andExpect(jsonPath("$[1].password", is(person2.getPassword())));
    }

    @Test
    public void whenGetAllThenNotFound() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDelete() throws Exception {
        this.mockMvc.perform(delete("/person/1"))
                .andExpect(status().isOk());
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository).delete(argument.capture());
        assertThat(argument.getValue().getId(), is(1));
    }

    @Test
    public void whenPut() throws Exception {
        this.mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\", \"login\":\"Person\",\"password\":\"Password\"}"))
                .andExpect(status().isOk());
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository).save(argument.capture());
        assertThat(argument.getValue().getId(), is(1));
        assertThat(argument.getValue().getLogin(), is("Person"));
        assertThat(argument.getValue().getPassword(), is("Password"));
    }

    @Test
    public void whenPost() throws Exception {
        Person person = new Person(1, "Person", "Password");
        when(repository.save(Mockito.any())).thenReturn(person);
        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"Person\",\"password\":\"Password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.id", is(person.getId())))
                .andExpect(jsonPath("$.login", is(person.getLogin())))
                .andExpect(jsonPath("$.password", is(person.getPassword())));
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("Person"));
        assertThat(argument.getValue().getPassword(), is("Password"));
    }

}