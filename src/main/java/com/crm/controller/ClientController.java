package com.crm.controller;

import com.crm.entity.Client;
import com.crm.entity.User;
import com.crm.repository.ClientRepository;
import com.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    // Список клиентов с фильтрацией
    @GetMapping
    public String listClients(
            @RequestParam(value = "statusFilter", required = false) String statusFilter,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        List<Client> clients;

        if (statusFilter != null && !statusFilter.isEmpty()) {
            // Фильтрация по статусу
            clients = clientRepository.findByStatus(statusFilter);
        } else if (search != null && !search.isEmpty()) {
            // Поиск по названию компании
            clients = clientRepository.findByCompanyNameContainingIgnoreCase(search);
        } else {
            // Все клиенты
            clients = clientRepository.findAll();
        }

        // Передаем статусы для фильтра
        String[] statuses = {"Активный", "Лид", "Неактивный"};

        model.addAttribute("clients", clients);
        model.addAttribute("statuses", statuses);
        model.addAttribute("currentFilter", statusFilter);
        model.addAttribute("searchParam", search);

        return "clients";
    }

    // Форма добавления
    @GetMapping("/new")
    public String showAddForm(Model model) {
        Client client = new Client();
        List<User> users = userRepository.findAll();

        model.addAttribute("client", client);
        model.addAttribute("users", users);
        return "add-client";
    }

    // Сохранение клиента
    @PostMapping
    public String saveClient(@ModelAttribute Client client,
                             @RequestParam Long responsibleUserId,
                             @RequestParam(required = false) String status) {
        User responsibleUser = userRepository.findById(responsibleUserId).orElse(null);
        client.setResponsibleUser(responsibleUser);

        // Устанавливаем статус (если не указан - по умолчанию "Лид")
        if (status != null && !status.isEmpty()) {
            client.setStatus(status);
        } else {
            client.setStatus("Лид");
        }

        clientRepository.save(client);
        return "redirect:/clients";
    }

    // Форма редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id).orElse(null);
        List<User> users = userRepository.findAll();

        model.addAttribute("client", client);
        model.addAttribute("users", users);
        return "edit-client";
    }

    // Обновление клиента
    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id,
                               @ModelAttribute Client client,
                               @RequestParam Long responsibleUserId,
                               @RequestParam String status) {
        client.setId(id);
        User responsibleUser = userRepository.findById(responsibleUserId).orElse(null);
        client.setResponsibleUser(responsibleUser);
        client.setStatus(status); // Обновляем статус

        clientRepository.save(client);
        return "redirect:/clients";
    }

    // Удаление клиента
    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }

    // Просмотр клиента
    @GetMapping("/view/{id}")
    public String viewClient(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id).orElse(null);
        model.addAttribute("client", client);
        return "view-client";
    }

    // Тестовый метод
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "ClientController работает! Количество клиентов: " + clientRepository.count();
    }
}