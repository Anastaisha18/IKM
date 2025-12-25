package com.crm.controller;

import com.crm.entity.User;
import com.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")  // Добавляем префикс для всех методов
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 1. Список пользователей - GET /users
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";  // users.html
    }

    // 2. Форма добавления - GET /users/new
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";  // add-user.html
    }

    // 3. Сохранение - POST /users
    @PostMapping
    public String saveUser(@ModelAttribute User user) {  // Используем @ModelAttribute
        System.out.println("Сохранение пользователя: " + user.getLastName() + " " + user.getFirstName());
        userRepository.save(user);
        return "redirect:/users";  // Перенаправляем на список
    }

    // 4. Форма редактирования - GET /users/edit/{id}
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "edit-user";  // edit-user.html
    }

    // 5. Обновление - POST /users/update/{id}
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id);
        userRepository.save(user);
        return "redirect:/users";
    }

    // 6. Удаление - GET /users/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    // 7. Просмотр - GET /users/view/{id}
    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "view-user";  // view-user.html
    }


    // 8. Тестовый метод
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Контроллер работает! Количество пользователей: " + userRepository.count();
    }
}