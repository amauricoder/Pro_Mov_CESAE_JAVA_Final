package com.manager.stock_manager.config;

import com.manager.stock_manager.model.Categoria;
import com.manager.stock_manager.model.Produto;
import com.manager.stock_manager.model.Role;
import com.manager.stock_manager.model.User;
import com.manager.stock_manager.repository.CategoriaRepository;
import com.manager.stock_manager.repository.ProdutoRepository;
import com.manager.stock_manager.repository.RoleRepository;
import com.manager.stock_manager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            RoleRepository roleRepository,
            CategoriaRepository categoriaRepository,
            ProdutoRepository produtoRepository) {
        return args -> {
            // Criar Roles se não existirem
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

            // Criar Usuários de teste
            if (userRepository.findByUsername("admin").isEmpty()) {
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                User admin = new User(null, "admin", passwordEncoder.encode("adminpass"), "admin@example.com", adminRoles);
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(userRole);
                User user = new User(null, "user", passwordEncoder.encode("userpass"), "user@example.com", userRoles);
                userRepository.save(user);
            }

            // Criar Categorias de exemplo
            if (categoriaRepository.count() == 0) {
                Categoria eletronicos = categoriaRepository.save(new Categoria(null, "Eletrônicos"));
                Categoria vestuario = categoriaRepository.save(new Categoria(null, "Vestuário"));

                // Criar Produtos de exemplo
             // Criar Produtos com os novos campos
                // Assinatura do construtor: id, nome, descricao, marca, dataAbertura, dataValidade, quantidade, valorCusto, precoVenda, categoria
                produtoRepository.save(new Produto(null, "Smartphone Z", "Smartphone de última geração com 5G", "TechCorp", LocalDate.now().minusMonths(1), LocalDate.now().plusYears(2), 50,  1599.99, eletronicos));
                produtoRepository.save(new Produto(null, "Camiseta Básica", "Camiseta 100% algodão, cor preta", "FashionBrand", LocalDate.now().minusDays(20), null, 200, 59.90, vestuario));
                produtoRepository.save(new Produto(null, "Laptop Pro", "Laptop de alta performance para trabalho", "GlobalPC", LocalDate.now().minusDays(10), LocalDate.now().plusYears(3), 30, 5200.00, eletronicos));
            }
        };
    }
}