package com.openclassrooms.moneytransfersystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.moneytransfersystem.model.User;
import com.openclassrooms.moneytransfersystem.service.user.UserReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserReadService userReadService;

    @Test
    public void shouldGetUsers() throws Exception {

        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetUserById() throws Exception {

        mockMvc.perform(get("/users/16")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetUserByEmail() throws Exception {

        mockMvc.perform(get("/user")
                .param("email", "david@test.com")).andExpect(status().isOk());
    }

    @Test
    public void shouldCreateUser() throws Exception {

        User user = new User();
        user.setEmail("harry@test.com");
        user.setPassword("123456");
        user.setFirstName("Harry");
        user.setLastName("POTTER");
        user.setIbanCode(123456);
        user.setBicCode(123456);

        mockMvc.perform(post("/createUser")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateUsers() throws Exception {

        User user = new User();
        user.setEmail("hermione@test.com");
        user.setPassword("123456");
        user.setFirstName("Hermione");
        user.setLastName("GRANGER");
        user.setIbanCode(123456);
        user.setBicCode(123456);

        List<User> users = new ArrayList<>();
        users.add(user);

        mockMvc.perform(post("/createUsers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(users)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateUser() throws Exception {

        User user = new User();
        user.setId(userReadService.readUserByEmail("harry@test.com").getId());
        user.setEmail("vernon@test.com");
        user.setPassword("123456");
        user.setFirstName("Vernon");
        user.setLastName("DURSLEY");
        user.setIbanCode(123456);
        user.setBicCode(123456);

        mockMvc.perform(put("/updateUser")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteUser() throws Exception {

        mockMvc.perform(delete("/users/1")).andExpect(status().isOk());
    }
}
