package com.example.finuslugi;

import com.example.finuslugi.model.Client;
import com.example.finuslugi.repository.ClientRepository;
import com.example.finuslugi.service.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    void setup() {
        MockHttpServletRequest request = MockMvcRequestBuilders.get("/").buildRequest(new MockServletContext());
        request.setAttribute("source", "mail");
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        clientService = new ClientServiceImpl(clientRepository, requestAttributes);
    }
    @Test
    void testCreateClient() {
        setup();

        Client client = new Client();
        client.setLastName("Max");
        client.setFirstName("Egr");
        client.setEmail("maxegr@example.com");

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client createdClient = clientService.createClient(client);

        assertNotNull(createdClient);
        assertEquals("Max", createdClient.getLastName());
        assertEquals("Egr", createdClient.getFirstName());
        assertEquals("maxegr@example.com", createdClient.getEmail());
    }

    @Test
    void testGetClientById() {

        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setLastName("Max");
        client.setFirstName("Egr");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client retrievedClient = clientService.getClientById(clientId);

        assertNotNull(retrievedClient);
        assertEquals(clientId, retrievedClient.getId());
        assertEquals("Max", retrievedClient.getLastName());
        assertEquals("Egr", retrievedClient.getFirstName());
    }

    @Test
    void testGetClientById_InvalidId() {

        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Client retrievedClient = clientService.getClientById(clientId);

        assertNull(retrievedClient);
    }

    @Test
    void testSearchClients() {

        String lastName = "Max";
        String firstName = "Egr";
        String phone = "71234567891";
        String email = "maxegr@example.com";

        List<Client> expectedClients = new ArrayList<>();
        Client client1 = new Client();
        client1.setId(1L);
        client1.setLastName(lastName);
        client1.setFirstName(firstName);
        client1.setPhone(phone);
        client1.setEmail(email);
        expectedClients.add(client1);

        when(clientRepository.searchClients(lastName, firstName, null, phone, email)).thenReturn(expectedClients);

        List<Client> foundClients = clientService.searchClients(lastName, firstName, null, phone, email);

        assertNotNull(foundClients);
        assertEquals(1, foundClients.size());

        Client foundClient = foundClients.get(0);
        assertEquals(1L, foundClient.getId());
        assertEquals(lastName, foundClient.getLastName());
        assertEquals(firstName, foundClient.getFirstName());
        assertEquals(phone, foundClient.getPhone());
        assertEquals(email, foundClient.getEmail());
    }
}