package com.example.finuslugi;

import com.example.finuslugi.controller.ClientController;
import com.example.finuslugi.model.Client;
import com.example.finuslugi.service.ClientServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ClientController clientController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .build();
    }

    @Test
    public void testCreateClient_Success() throws Exception {

        Client client = new Client();
        client.setLastName("Egr");
        client.setFirstName("Max");
        client.setMiddleName("Serg");

        Client createdClient = new Client();
        createdClient.setId(1L);
        createdClient.setLastName(client.getLastName());
        createdClient.setFirstName(client.getFirstName());
        createdClient.setMiddleName(client.getMiddleName());

        Mockito.when(clientService.createClient(Mockito.any(Client.class))).thenReturn(createdClient);

        mockMvc.perform(MockMvcRequestBuilders.post("/client")
                        .header("x-Source", "source-value") // Задаем заголовок "x-Source"
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdClient.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(createdClient.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(createdClient.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.middleName").value(createdClient.getMiddleName()));
    }

    @Test
    public void testGetClientById_Success() throws Exception {
        Long clientId = 1L;

        Client client = new Client();
        client.setId(clientId);
        client.setLastName("Egr");
        client.setFirstName("Max");
        client.setMiddleName("Serg");

        Mockito.when(clientService.getClientById(clientId)).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/{id}", clientId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(clientId.intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(client.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.middleName").value(client.getMiddleName()));
    }

    @Test
    public void testGetClientById_NotFound() throws Exception {
        Long clientId = 100L;

        Mockito.when(clientService.getClientById(clientId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/{id}", clientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testSearchClients_Success() throws Exception {
        String lastName = "Egr";
        String firstName = "Max";
        String middleName = "Serg";

        List<Client> clients = new ArrayList<>();
        Client client1 = new Client();
        client1.setId(1L);
        client1.setLastName(lastName);
        client1.setFirstName(firstName);
        client1.setMiddleName(middleName);
        clients.add(client1);

        Mockito.when(clientService.searchClients(lastName, firstName, middleName, null, null)).thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/search")
                        .param("lastName", lastName)
                        .param("firstName", firstName)
                        .param("middleName", middleName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(client1.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(client1.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(client1.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].middleName").value(client1.getMiddleName()));
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
