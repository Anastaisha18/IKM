package com.crm.repository;

import com.crm.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Метод для фильтрации по статусу
    List<Client> findByStatus(String status);

    // Метод для поиска по компании (опционально)
    List<Client> findByCompanyNameContainingIgnoreCase(String companyName);
}
