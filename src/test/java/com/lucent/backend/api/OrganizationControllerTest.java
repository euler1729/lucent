package com.lucent.backend.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private final String phone = "111111118", name="Organizer", password="test", orgName="Org", orgDescription="Desc", adminPhone= "01782267068", adminPassword="admin";

    public String login(Boolean isAdmin) throws Exception {
        MvcResult response;
        if(isAdmin) {
            response = mockMvc.perform(
                            get("/user/login")
                                    .contentType(MULTIPART_FORM_DATA_VALUE)
                                    .param("phone", adminPhone)
                                    .param("password", adminPassword)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        }
        else{
            response = mockMvc.perform(
                            get("/user/login")
                                    .contentType(MULTIPART_FORM_DATA_VALUE)
                                    .param("phone", phone)
                                    .param("password", password)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        }
        String stringResponse = response.getResponse().getContentAsString();
        JsonObject jsonResponse = new Gson().fromJson(stringResponse, JsonObject.class);
        String access_token = String.valueOf(jsonResponse.get("access_token"));
        access_token = access_token.substring(1, access_token.length() -1);
        return access_token;
    }

    @Test @Order(1)
    void registerOrg() throws Exception {
//        OrganizationRegistrationForm form = new OrganizationRegistrationForm(phone, name, password, orgName, orgDescription);
//        Gson gson = new Gson();
//        String requestJson = gson.toJson(form);
//
//        MvcResult response = mockMvc.perform(
//                post("/org/registration")
//                        .contentType(APPLICATION_JSON)
//                        .content(requestJson)
//        ).andExpect(status().isCreated()).andReturn();
    }

    @Test @Order(2)
    void getPublishedOrg() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/org/published")
        ).andExpect(status().is2xxSuccessful()).andExpect(content().string("[]")).andReturn();
    }

    /***
     * Assumes database is clear
     */
    @Test @Order(3)
    void publishOrg() throws Exception {

        String nonAdminToken = login(false);

        MvcResult result = mockMvc.perform(
                post("/org/publish/1")
                        .header("AUTHORIZATION", "Bearer " + nonAdminToken)
        ).andExpect(status().is4xxClientError()).andReturn();

        String adminToken = login(true);

        result = mockMvc.perform(
                post("/org/publish/1")
                        .header("AUTHORIZATION", "Bearer " + adminToken)
        ).andExpect(status().is2xxSuccessful()).andReturn();
    }

    @Test @Order(4)
    void getAllOrg() throws Exception {
        String adminToken = login(true);
        MvcResult result = mockMvc.perform(
                get("/org/all")
                        .header("AUTHORIZATION", "Bearer " + adminToken)
        ).andExpect(status().is2xxSuccessful()).andReturn();
    }

    @Test @Order(5)
    void updateOrg() throws Exception {
        OrganizationUpdateForm form = new OrganizationUpdateForm(orgDescription, true, true, true);
        Gson gson = new Gson();
        String requestJson = gson.toJson(form);

        String managerToken = login(false);
        MvcResult result = mockMvc.perform(
                put("/org/update/1")
                        .header("AUTHORIZATION", "Bearer " + managerToken)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
        ).andExpect(status().is2xxSuccessful()).andReturn();
    }
}