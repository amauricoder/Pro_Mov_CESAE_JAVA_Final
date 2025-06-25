package com.manager.stock_manager.repository;

import com.manager.stock_manager.model.Produto;
import com.manager.stock_manager.model.User; // Importação necessária
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Importação necessária

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Novo método para encontrar todos os produtos que pertencem a um usuário específico.
     * O Spring Data JPA cria a query automaticamente a partir do nome do método.
     */
    List<Produto> findByUser(User user);
}