package com.crm.controller;

import com.crm.repository.ClientRepository;
import com.crm.repository.DealRepository;
import com.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DealRepository dealRepository;

    @GetMapping({"/"})
    public String home(Model model) {
        model.addAttribute("userCount", this.userRepository.count());
        model.addAttribute("clientCount", this.clientRepository.count());
        model.addAttribute("dealCount", this.dealRepository.count());
        return "dashboard";
    }

    @GetMapping({"/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("userCount", this.userRepository.count());
        model.addAttribute("clientCount", this.clientRepository.count());
        model.addAttribute("dealCount", this.dealRepository.count());
        return "dashboard";
    }
}
