package com.example.finuslugi;

import com.example.finuslugi.model.Client;
import com.example.finuslugi.service.ClientServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Date;


public class ClientServiceValidateTest {
    @Test
    public void testValidateMailClientFields() {
        ClientServiceImpl clientService = new ClientServiceImpl(null, null);

        Client client1 = new Client();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateMailClientFields(client1);
        });

        Client client2 = new Client();
        client2.setEmail("maxegr@example.com");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateMailClientFields(client2);
        });

        Client client3 = new Client();
        client3.setFirstName("Max");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateMailClientFields(client3);
        });

        Client client4 = new Client();
        client4.setFirstName("Max");
        client4.setEmail("maxegr@example.com");
        Assertions.assertDoesNotThrow(() -> {
            clientService.validateMailClientFields(client4);
        });
    }

    @Test
    public void testValidateMobileClientFields() {
        ClientServiceImpl clientService = new ClientServiceImpl(null, null);

        Client client1 = new Client();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateMobileClientFields(client1);
        });

        Client client2 = new Client();
        client2.setPhone("71234567891");
        Assertions.assertDoesNotThrow(() -> {
            clientService.validateMobileClientFields(client2);
        });
    }

    @Test
    public void testValidateBankClientFields() {
        ClientServiceImpl clientService = new ClientServiceImpl(null, null);

        Client client1 = new Client();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateBankClientFields(client1);
        });

        Client client2 = new Client();
        client2.setBankId("123456");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateBankClientFields(client2);
        });

        Client client3 = new Client();
        client3.setBankId("123456");
        client3.setLastName("Egr");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateBankClientFields(client3);
        });

        Client client4 = new Client();
        client4.setBankId("123456");
        client4.setLastName("Egr");
        client4.setFirstName("Max");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateBankClientFields(client4);
        });

        Client client5 = new Client();
        client5.setBankId("123456");
        client5.setLastName("Egr");
        client5.setFirstName("Max");
        client5.setMiddleName("Serg");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateBankClientFields(client5);
        });

        Client client6 = new Client();
        client6.setBankId("123456");
        client6.setLastName("Egr");
        client6.setFirstName("Max");
        client6.setMiddleName("Serg");
        client6.setBirthDate(new Date());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateBankClientFields(client6);
        });

        Client client7 = new Client();
        client7.setBankId("123456");
        client7.setLastName("Egr");
        client7.setFirstName("Max");
        client7.setMiddleName("Serg");
        client7.setBirthDate(new Date());
        client7.setPassportNumber("1234 123456");
        Assertions.assertDoesNotThrow(() -> {
            clientService.validateBankClientFields(client7);
        });
    }

    @Test
    public void testValidateGosuslugiClientFields() {
        ClientServiceImpl clientService = new ClientServiceImpl(null, null);

        Client client1 = new Client();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client1);
        });

        Client client2 = new Client();
        client2.setLastName("Egr");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client2);
        });

        Client client3 = new Client();
        client3.setLastName("Egr");
        client3.setFirstName("Max");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client3);
        });

        Client client4 = new Client();
        client4.setLastName("Egr");
        client4.setFirstName("Max");
        client4.setMiddleName("Serg");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client4);
        });

        Client client5 = new Client();
        client5.setLastName("Egr");
        client5.setFirstName("Max");
        client5.setMiddleName("Serg");
        client5.setBirthDate(new Date());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client5);
        });

        Client client6 = new Client();
        client6.setLastName("Egr");
        client6.setFirstName("Max");
        client6.setMiddleName("Serg");
        client6.setBirthDate(new Date());
        client6.setPassportNumber("1234 123456");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client6);
        });

        Client client7 = new Client();
        client7.setLastName("Egr");
        client7.setFirstName("Max");
        client7.setMiddleName("Serg");
        client7.setBirthDate(new Date());
        client7.setPassportNumber("1234 123456");
        client7.setPhone("71234567891");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client7);
        });

        Client client8 = new Client();
        client8.setLastName("Egr");
        client8.setFirstName("Max");
        client8.setMiddleName("Serg");
        client8.setBirthDate(new Date());
        client8.setPassportNumber("1234 123456");
        client8.setPhone("71234567891");
        client8.setBankId("123456");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.validateGosuslugiClientFields(client8);
        });

        Client client9 = new Client();
        client9.setLastName("Egr");
        client9.setFirstName("Max");
        client9.setMiddleName("Serg");
        client9.setBirthDate(new Date());
        client9.setPassportNumber("1234 123456");
        client9.setPhone("71234567891");
        client9.setBankId("123456");
        client9.setRegistrationAddress("123 Born St");
        Assertions.assertDoesNotThrow(() -> {
            clientService.validateGosuslugiClientFields(client9);
        });
    }
}
