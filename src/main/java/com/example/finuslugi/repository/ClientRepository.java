package com.example.finuslugi.repository;

import com.example.finuslugi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE (:lastName IS NULL OR c.lastName = :lastName) " +
            "AND (:firstName IS NULL OR c.firstName = :firstName) " +
            "AND (:middleName IS NULL OR c.middleName = :middleName) " +
            "AND (:phone IS NULL OR c.phone = :phone) " +
            "AND (:email IS NULL OR c.email = :email)")
    List<Client> searchClients(@Param("lastName") String lastName,
                               @Param("firstName") String firstName,
                               @Param("middleName") String middleName,
                               @Param("phone") String phone,
                               @Param("email") String email);
}