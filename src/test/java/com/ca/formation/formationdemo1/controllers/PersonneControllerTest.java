package com.ca.formation.formationdemo1.controllers;

import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.services.PersonneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonneControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  PersonneService personneService;

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  PersonneController personneController;

  @LocalServerPort
  private int port;

  private String getRootUrl() {
    return "http://localhost:" + port + "/api/v2";
  }

  private String tokenRequest;

 /* @Test
    @WithMockUser(username = "clara@formation.sn", password = "Passer@123", authorities = { "ADMIN" })
    public void byeTest() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:8082"+"/api/v2/personnes/bye",
                HttpMethod.GET, entity, String.class);

        System.out.println("+++++++++++++++++++" +tokenRequest+" ++++++++++");
        assertEquals(response.getBody(), "Bye bye");

    }*/

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
  public void getPersonnes() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void getPersonnesPasAutoriser() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());

  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
  public void getPersonnesVrai() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/v2/personnes",
            HttpMethod.GET, entity, String.class);

    assertNotNull(response);

  }

  @Test
  public void getPersonne_() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<Personne> responseEntity = restTemplate.exchange(getRootUrl() + "/personnes/3", HttpMethod.GET,
            entity, Personne.class);

    assertNotNull(responseEntity);
    // assertEquals(personne.getNom(), "Abdel");

  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "ADMIN" })
  public void ajouterPersonne_() throws Exception {
    Personne personne = new Personne("tonux", "samb", 40);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<Personne> entity = new HttpEntity<Personne>(null, headers);
    ResponseEntity<Personne> responseEntity = restTemplate
            .exchange(getRootUrl() + "/personnes", HttpMethod.POST, entity, Personne.class, personne);
    assertNotNull(responseEntity);
  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
  public void getPersonne() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/2")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();
    assertNotNull(contentAsString);
  }

  @Test
  @Before
  public void login() throws Exception {
    String body = "{\n" +
            "    \"username\": \"clara@formation.sn\",\n" +
            "    \"password\": \"Passer@123\"\n" +
            "}";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v2/auth/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String token = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    tokenRequest = token;
    //System.out.println("++++++++++++++++++++" + tokenRequest+"+++++++++++++++");
  }

  //methode ajouté

  @Test
  public void getPersonnes_() throws Exception {
  }

  @Test
  public void ajouterPesonne() throws Exception {
    Personne personne = new Personne();
    personne.setAge(15);
    personne.setId(15L);
    personne.setNom("TOMAVO");
    personne.setPrenom("Clarisse");
    when(personneService.addPersonne((Personne) any())).thenReturn(personne);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ajouterPersonne");
    MockMvcBuilders.standaloneSetup(personneController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("personne"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
  }

  @Test
  public void nouveauPersonne() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/nouveau").param("age", "Age");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(personneController)
            .build()
            .perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
  }


}
