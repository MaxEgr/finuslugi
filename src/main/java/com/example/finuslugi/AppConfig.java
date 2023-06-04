package com.example.finuslugi;

import com.example.finuslugi.repository.ClientRepository;
import com.example.finuslugi.service.ClientService;
import com.example.finuslugi.service.ClientServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class AppConfig {

    @Bean
    public RequestAttributes requestAttributes() {
        return new ServletRequestAttributes(new MockHttpServletRequest());
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository, RequestAttributes requestAttributes) {
        return new ClientServiceImpl(clientRepository, requestAttributes);
    }
}

