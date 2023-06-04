package com.example.finuslugi.service;

import com.example.finuslugi.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    Client createClient(Client client);
    Client getClientById(Long id);
    List<Client> searchClients(String lastName, String firstName, String middleName, String phone, String email);
}
