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

    // Список клиентов
    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
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
                             @RequestParam Long responsibleUserId) {
        User responsibleUser = userRepository.findById(responsibleUserId).orElse(null);
        client.setResponsibleUser(responsibleUser);
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
                               @RequestParam Long responsibleUserId) {
        client.setId(id);
        User responsibleUser = userRepository.findById(responsibleUserId).orElse(null);
        client.setResponsibleUser(responsibleUser);
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