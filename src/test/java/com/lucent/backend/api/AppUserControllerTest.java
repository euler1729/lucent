package com.lucent.backend.api;

import com.google.gson.Gson;
import com.lucent.backend.api.dto.AppUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    public void registrationTest() throws Exception {

        AppUserRequest requestUser = new AppUserRequest("testuser", "testuser@email.com", "test");

        Gson gson = new Gson();
        String requestJson = gson.toJson(requestUser);

        this.mockMvc.perform(
                post("/user/registration")
                    .contentType(APPLICATION_JSON)
                    .content(requestJson)
        ).andDo(print()).andExpect(status().isCreated());
    }

}