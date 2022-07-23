package com.lucent.backend.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucent.backend.api.dto.AppUserRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private final String phone = "0111111111", password = "test";;
    private String access_token, refresh_token;

    @Test
    @Order(1)
    public void registrationTest() throws Exception {

        AppUserRequest requestUser = new AppUserRequest("testuser", phone, password);

        Gson gson = new Gson();
        String requestJson = gson.toJson(requestUser);

//        Registration test
        this.mockMvc.perform(
                post("/user/registration")
                    .contentType(APPLICATION_JSON)
                    .content(requestJson)
        ).andExpect(status().isCreated());

//         Duplicate phone test
        this.mockMvc.perform(
                post("/user/registration")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    public String loginTest() throws Exception {
        MvcResult response = mockMvc.perform(
                get("/user/login")
                        .contentType(MULTIPART_FORM_DATA_VALUE)
                        .param("phone", phone)
                        .param("password", password)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String stringResponse = response.getResponse().getContentAsString();
        JsonObject jsonResponse = new Gson().fromJson(stringResponse, JsonObject.class);
        refresh_token = String.valueOf(jsonResponse.get("refresh_token"));
        refresh_token = refresh_token.substring(1, refresh_token.length() -1); // slicing " "
        return refresh_token;
    }

    @Test
    @Order(3)
    public String testRefreshToken() throws Exception {
        String authorization = "Bearer " + loginTest();
        assertNotNull(refresh_token);
        MvcResult response = mockMvc.perform(
            get("/token/refresh")
                    .header("AUTHORIZATION", authorization)
        ).andExpect(status().is2xxSuccessful()).andReturn();

        String stringResponse = response.getResponse().getContentAsString();
        JsonObject jsonResponse = new Gson().fromJson(stringResponse, JsonObject.class);
        access_token = String.valueOf(jsonResponse.get("access_token"));
        access_token = access_token.substring(1, access_token.length() -1); // slicing " "
        return access_token;
    }

    @Test
    @Order(4)
    public void testProfile() throws Exception {
        String authorization = "Bearer " + testRefreshToken();
        assertNotNull(refresh_token);
        MvcResult response = mockMvc.perform(
                get("/user/profile")
                        .header("AUTHORIZATION", authorization)
        ).andExpect(status().is2xxSuccessful()).andReturn();
    }

}