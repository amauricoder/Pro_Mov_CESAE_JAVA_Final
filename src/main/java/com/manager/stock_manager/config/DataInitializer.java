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
            // 1. CRIAR ROLES
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

            // 2. CRIAR USUÁRIOS USANDO SETTERS (MÉTODO CORRIGIDO E MAIS ROBUSTO)
            User admin = userRepository.findByUsername("admin")
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername("admin");
                        newUser.setPassword(passwordEncoder.encode("adminpass"));
                        newUser.setEmail("admin@example.com");
                        newUser.setRoles(Set.of(adminRole, userRole));
                        return userRepository.save(newUser);
                    });

            User ana = userRepository.findByUsername("ana.silva")
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername("ana.silva");
                        newUser.setPassword(passwordEncoder.encode("anapass"));
                        newUser.setEmail("ana.silva@example.com");
                        newUser.setRoles(Set.of(userRole));
                        return userRepository.save(newUser);
                    });
            
            User bruno = userRepository.findByUsername("bruno.costa")
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername("bruno.costa");
                        newUser.setPassword(passwordEncoder.encode("brunopass"));
                        newUser.setEmail("bruno.costa@example.com");
                        newUser.setRoles(Set.of(userRole));
                        return userRepository.save(newUser);
                    });

            User carla = userRepository.findByUsername("carla.dias")
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername("carla.dias");
                        newUser.setPassword(passwordEncoder.encode("carlapass"));
                        newUser.setEmail("carla.dias@example.com");
                        newUser.setRoles(Set.of(userRole));
                        return userRepository.save(newUser);
                    });

            // 3. CRIAR CATEGORIAS E PRODUTOS
            if (produtoRepository.count() == 0) {
                Categoria eletronicos = categoriaRepository.save(new Categoria(null, "Eletrônicos"));
                Categoria vestuario = categoriaRepository.save(new Categoria(null, "Vestuário"));
                Categoria escritorio = categoriaRepository.save(new Categoria(null, "Material de Escritório"));

                // O construtor do Produto agora também está correto
                // id, nome, descricao, marca, dataAbertura, dataValidade, quantidade, preco, categoria, user

                // Produtos para 'admin'
                produtoRepository.save(new Produto(null, "Servidor PowerEdge", "Servidor de Rack Dell", "Dell", LocalDate.now().minusDays(10), null, 5, 8500.00, eletronicos, admin));
                produtoRepository.save(new Produto(null, "Cadeira de Escritório Ergonômica", "Cadeira com suporte lombar", "FlexForm", LocalDate.now().minusDays(5), null, 15, 1200.00, escritorio, admin));
                produtoRepository.save(new Produto(null, "Switch 24 Portas", "Switch de rede Gigabit", "TP-Link", LocalDate.now().minusMonths(1), null, 10, 800.00, eletronicos, admin));
                produtoRepository.save(new Produto(null, "Fragmentadora de Papel", "Destrói até 10 folhas", "OfficePro", LocalDate.now().minusWeeks(1), null, 8, 350.00, escritorio, admin));

                // Produtos para 'ana.silva'
                produtoRepository.save(new Produto(null, "Laptop Slim", "Laptop leve e fino para trabalho", "GlobalPC", LocalDate.now().minusDays(20), LocalDate.now().plusYears(3), 1, 4500.00, eletronicos, ana));
                produtoRepository.save(new Produto(null, "Tênis de Corrida", "Tênis com amortecimento", "Runner", LocalDate.now().minusDays(3), null, 2, 350.00, vestuario, ana));
                
                // Adicione mais produtos para bruno e carla se desejar, seguindo o mesmo padrão...
            }
        };
    }
}