package com.example.finuslugi.controller;

import com.example.finuslugi.model.Client;
import com.example.finuslugi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client, @RequestHeader("x-Source") String source) {
        Client createdClient = clientService.createClient(client);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(@RequestParam(required = false) String lastName,
                                                      @RequestParam(required = false) String firstName,
                                                      @RequestParam(required = false) String middleName,
                                                      @RequestParam(required = false) String phone,
                                                      @RequestParam(required = false) String email) {
        List<Client> clients = clientService.searchClients(lastName, firstName, middleName, phone, email);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
