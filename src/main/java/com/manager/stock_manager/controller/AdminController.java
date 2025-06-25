package com.manager.stock_manager.controller;

import com.manager.stock_manager.model.User;
import com.manager.stock_manager.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin") // Todas as rotas neste controller começarão com /admin
public class AdminController {

    private final UserRepository userRepository;

    // Injeta o repositório para podermos buscar os usuários no banco
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Mapeia a requisição GET para /admin/users
    @GetMapping("/users")
    public String showUserList(Model model) {
        // Busca todos os usuários do banco de dados
        List<User> userList = userRepository.findAll();

        // Adiciona a lista de usuários ao "Model". O Model é o que leva os dados do Controller para a View (HTML).
        model.addAttribute("users", userList);

        // Retorna o nome do arquivo HTML que deve ser renderizado.
        // O Spring (com Thymeleaf) irá procurar por: /resources/templates/admin/user-list.html
        return "admin/user-list";
    }

    // Você pode adicionar outras rotas de admin aqui, como /admin/products, etc.
}