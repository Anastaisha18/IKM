package com.crm.controller;

import com.crm.entity.Client;
import com.crm.entity.Deal;
import com.crm.entity.User;
import com.crm.repository.ClientRepository;
import com.crm.repository.DealRepository;
import com.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/deals")
public class DealController {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    // Список сделок
    @GetMapping
    public String listDeals(Model model) {
        List<Deal> deals = dealRepository.findAll();
        model.addAttribute("deals", deals);

        // Рассчитать общую сумму сделок
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Deal deal : deals) {
            if (deal.getAmount() != null) {
                totalAmount = totalAmount.add(deal.getAmount());
            }
        }
        model.addAttribute("totalAmount", totalAmount);

        return "deals";
    }

    // Форма добавления сделки
    @GetMapping("/new")
    public String showAddForm(Model model) {
        Deal deal = new Deal();
        List<Client> clients = clientRepository.findAll();
        List<User> users = userRepository.findAll();

        model.addAttribute("deal", deal);
        model.addAttribute("clients", clients);
        model.addAttribute("users", users);
        return "add-deal";
    }

    // Сохранение сделки
    @PostMapping
    public String saveDeal(@ModelAttribute Deal deal,
                           @RequestParam Long clientId,
                           @RequestParam Long responsibleUserId,
                           @RequestParam String amount) {
        Client client = clientRepository.findById(clientId).orElse(null);
        User responsibleUser = userRepository.findById(responsibleUserId).orElse(null);

        deal.setClient(client);
        deal.setResponsibleUser(responsibleUser);
        deal.setAmount(new BigDecimal(amount));

        dealRepository.save(deal);
        return "redirect:/deals";
    }

    // Форма редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Deal deal = dealRepository.findById(id).orElse(null);
        List<Client> clients = clientRepository.findAll();
        List<User> users = userRepository.findAll();

        model.addAttribute("deal", deal);
        model.addAttribute("clients", clients);
        model.addAttribute("users", users);
        return "edit-deal";
    }

    // Обновление сделки
    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id,
                             @ModelAttribute Deal deal,
                             @RequestParam Long clientId,
                             @RequestParam Long responsibleUserId,
                             @RequestParam String amount) {
        deal.setId(id);
        Client client = clientRepository.findById(clientId).orElse(null);
        User responsibleUser = userRepository.findById(responsibleUserId).orElse(null);

        deal.setClient(client);
        deal.setResponsibleUser(responsibleUser);
        deal.setAmount(new BigDecimal(amount));

        dealRepository.save(deal);
        return "redirect:/deals";
    }

    // Удаление сделки
    @GetMapping("/delete/{id}")
    public String deleteDeal(@PathVariable Long id) {
        dealRepository.deleteById(id);
        return "redirect:/deals";
    }

    // Просмотр сделки
    @GetMapping("/view/{id}")
    public String viewDeal(@PathVariable Long id, Model model) {
        Deal deal = dealRepository.findById(id).orElse(null);
        model.addAttribute("deal", deal);
        return "view-deal";
    }

    // Тестовый метод
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "DealController работает! Количество сделок: " + dealRepository.count();
    }
}