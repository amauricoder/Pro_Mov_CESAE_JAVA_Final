package com.manager.stock_manager.repository;


import com.manager.stock_manager.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}