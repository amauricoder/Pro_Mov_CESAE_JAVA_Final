package com.manager.stock_manager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	private String nome;

	private String descricao;

	@NotBlank(message = "A marca é obrigatória")
	private String marca;

	@PastOrPresent(message = "A data de abertura não pode ser uma data futura")
	private LocalDate dataDeAbertura;

	@Future(message = "A data de validade deve ser uma data futura")
	private LocalDate dataDeValidade;

	@Min(value = 0, message = "A quantidade não pode ser negativa")
	private int quantidade;

	@Min(value = 0, message = "O preço de venda não pode ser negativo")
	private double preco;

	@NotNull(message = "A categoria é obrigatória")
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	// Este campo cria a ligação entre o produto e o usuário que o criou.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}