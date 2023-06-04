package com.example.finuslugi.service;

import com.example.finuslugi.model.Client;
import com.example.finuslugi.repository.ClientRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RequestAttributes requestAttributes;
    private String source;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RequestAttributes requestAttributes) {
        this.clientRepository = clientRepository;
        this.requestAttributes = requestAttributes;
    }
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public Client createClient(Client client) {
        String source = getSourceFromRequest(requestAttributes);
        setSource(source);
        validateClientFields(client, source);
        return clientRepository.save(client);
    }
    private String getSourceFromRequest(RequestAttributes requestAttributes) {
        if (requestAttributes != null) {
            Object source = requestAttributes.getAttribute("source", RequestAttributes.SCOPE_REQUEST);
            if (source != null && source instanceof String) {
                return (String) source;
            }
        }
        return null;
    }

    @Override
    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    @Override
    public List<Client> searchClients(String lastName, String firstName, String middleName, String phone, String email) {
        return clientRepository.searchClients(lastName, firstName, middleName, phone, email);
    }

    private void validateClientFields(Client client, String source) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        if (source == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }
        switch (source) {
            case "mail":
                validateMailClientFields(client);
                break;
            case "mobile":
                validateMobileClientFields(client);
                break;
            case "bank":
                validateBankClientFields(client);
                break;
            case "gosuslugi":
                validateGosuslugiClientFields(client);
                break;
            default:
                throw new IllegalArgumentException("Invalid source");
        }
    }

    public void validateMailClientFields(Client client) {

        if (StringUtils.isBlank(client.getFirstName()) && StringUtils.isBlank(client.getEmail())) {
            throw new IllegalArgumentException("First name and email are required for 'mail' source");
        } else if (StringUtils.isBlank(client.getFirstName())) {
            throw new IllegalArgumentException("First name is required for 'mail' source");
        } else if (StringUtils.isBlank(client.getEmail())) {
            throw new IllegalArgumentException("Email is required for 'mail' source");
        }
    }

    public void validateMobileClientFields(Client client) {

        if (StringUtils.isBlank(client.getPhone())) {
            throw new IllegalArgumentException("Phone number is required for 'mobile' source");
        }
    }

    public void validateBankClientFields(Client client) {

        if (StringUtils.isBlank(client.getBankId())) {
            throw new IllegalArgumentException("Bank ID is required for 'bank' source");
        }
        if (StringUtils.isBlank(client.getLastName())) {
            throw new IllegalArgumentException("Last name is required for 'bank' source");
        }
        if (StringUtils.isBlank(client.getFirstName())) {
            throw new IllegalArgumentException("First name is required for 'bank' source");
        }
        if (StringUtils.isBlank(client.getMiddleName())) {
            throw new IllegalArgumentException("Middle name is required for 'bank' source");
        }
        if (client.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date is required for 'bank' source");
        }
        if (StringUtils.isBlank(client.getPassportNumber())) {
            throw new IllegalArgumentException("Passport number is required for 'bank' source");
        }
    }

    public void validateGosuslugiClientFields(Client client) {

        if (StringUtils.isBlank(client.getLastName())) {
            throw new IllegalArgumentException("Last name is required for 'gosuslugi' source");
        }
        if (StringUtils.isBlank(client.getFirstName())) {
            throw new IllegalArgumentException("First name is required for 'gosuslugi' source");
        }
        if (StringUtils.isBlank(client.getMiddleName())) {
            throw new IllegalArgumentException("Middle name is required for 'gosuslugi' source");
        }
        if (client.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date is required for 'gosuslugi' source");
        }
        if (StringUtils.isBlank(client.getPassportNumber())) {
            throw new IllegalArgumentException("Passport number is required for 'gosuslugi' source");
        }
        if (StringUtils.isBlank(client.getPhone())) {
            throw new IllegalArgumentException("Phone number is required for 'gosuslugi' source");
        }
        if (StringUtils.isBlank(client.getBankId())) {
            throw new IllegalArgumentException("Bank ID is required for 'gosuslugi' source");
        }
        if (StringUtils.isBlank(client.getRegistrationAddress())) {
            throw new IllegalArgumentException("Registration address is required for 'gosuslugi' source");
        }
    }


    private String getSourceFromRequest() {
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request.getHeader("x-Source");
        }
        throw new IllegalStateException("RequestAttributes not found");
    }
}


