package com.manager.stock_manager.controller;

import com.manager.stock_manager.model.Categoria;
import com.manager.stock_manager.model.Produto;
import com.manager.stock_manager.model.User;
import com.manager.stock_manager.repository.CategoriaRepository;
import com.manager.stock_manager.repository.ProdutoRepository;
import com.manager.stock_manager.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoRepository produtoRepository;
	private final CategoriaRepository categoriaRepository;
	private final UserRepository userRepository;

	public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, UserRepository userRepository) {
		this.produtoRepository = produtoRepository;
		this.categoriaRepository = categoriaRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/list")
	public String listProdutos(Model model, Authentication authentication) {
		String username = authentication.getName();
		User currentUser = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalStateException("Usuário logado não encontrado no banco de dados."));
		List<Produto> produtos;
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			produtos = produtoRepository.findAll();
		} else {
			produtos = produtoRepository.findByUser(currentUser);
		}
		model.addAttribute("produtos", produtos);
		return "produtos/list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/new")
	public String showAddForm(Model model) {
		model.addAttribute("produto", new Produto());
		model.addAttribute("categorias", categoriaRepository.findAll());
		return "produtos/add-edit";
	}

	/**
	 * Salva um produto. Agora verifica a posse se for uma edição.
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/save")
	public String saveProduto(
			@Valid @ModelAttribute("produto") Produto produto,
			BindingResult bindingResult,
			@RequestParam(name = "novaCategoriaNome", required = false) String novaCategoriaNome,
			Model model,
			Authentication authentication) {
		
		String username = authentication.getName();
		User currentUser = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalStateException("Usuário logado não encontrado."));

		// --- LÓGICA DE VERIFICAÇÃO DE POSSE (PARA EDIÇÃO) ---
		if (produto.getId() != null) { // Se o ID não é nulo, é uma edição.
			Produto produtoExistente = produtoRepository.findById(produto.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
			// Verifica se o usuário logado é o dono do produto OU se é um ADMIN.
			if (!produtoExistente.getUser().getUsername().equals(username) && !isAdmin(authentication)) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar este produto.");
			}
		}
		
		// Lógica da categoria
		if (novaCategoriaNome != null && !novaCategoriaNome.isBlank()) {
			Categoria novaCategoria = new Categoria();
			novaCategoria.setNome(novaCategoriaNome);
			produto.setCategoria(categoriaRepository.save(novaCategoria));
		} else if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
			bindingResult.rejectValue("categoria", "error.produto", "É necessário selecionar ou criar uma categoria.");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("categorias", categoriaRepository.findAll());
			return "produtos/add-edit";
		}
		
		// Associa (ou reassocia) o produto ao usuário atual ANTES de salvar
		produto.setUser(currentUser);
		produtoRepository.save(produto);
		return "redirect:/produtos/list";
	}

	/**
	 * Exibe o formulário de edição após verificar a posse.
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model, Authentication authentication) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID de produto inválido:" + id));

		// Verifica se o usuário logado é o dono do produto OU se é um ADMIN.
		if (!produto.getUser().getUsername().equals(authentication.getName()) && !isAdmin(authentication)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar este produto.");
		}

		model.addAttribute("produto", produto);
		model.addAttribute("categorias", categoriaRepository.findAll());
		return "produtos/add-edit";
	}

	/**
	 * Exclui um produto após verificar a posse.
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deleteProduto(@PathVariable("id") Long id, Authentication authentication) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID de produto inválido:" + id));
		
		// Verifica se o usuário logado é o dono do produto OU se é um ADMIN.
		if (!produto.getUser().getUsername().equals(authentication.getName()) && !isAdmin(authentication)) {
			 throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir este produto.");
		}

		produtoRepository.deleteById(id);
		return "redirect:/produtos/list";
	}
	
	// Método auxiliar para verificar se o usuário é admin
	private boolean isAdmin(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
	}
}