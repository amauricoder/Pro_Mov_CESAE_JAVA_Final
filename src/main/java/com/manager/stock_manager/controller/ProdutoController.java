package com.manager.stock_manager.controller;

import com.manager.stock_manager.model.Categoria;
import com.manager.stock_manager.model.Produto;
import com.manager.stock_manager.repository.CategoriaRepository;
import com.manager.stock_manager.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Este método não precisa de alterações.
    @GetMapping("/list")
    public String listProdutos(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtos);
        return "produtos/list";
    }

    // Este método não precisa de alterações.
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "produtos/add-edit";
    }

    // --- ESTE É O MÉTODO QUE FOI MODIFICADO ---
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String saveProduto(
            @Valid @ModelAttribute("produto") Produto produto,
            BindingResult bindingResult,
            // 1. Adicionamos este @RequestParam para pegar o valor do campo de texto da nova categoria.
            @RequestParam(name = "novaCategoriaNome", required = false) String novaCategoriaNome,
            Model model) {

        // 2. Adicionamos a lógica para tratar a categoria antes de validar o resultado.
        Categoria categoriaFinal;
        if (novaCategoriaNome != null && !novaCategoriaNome.isBlank()) {
            // Se o usuário digitou um nome para uma nova categoria, nós a criamos.
            Categoria novaCategoria = new Categoria();
            novaCategoria.setNome(novaCategoriaNome);
            categoriaFinal = categoriaRepository.save(novaCategoria);
            produto.setCategoria(categoriaFinal); // Associa imediatamente ao produto
        } else if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            // Se o usuário não criou uma nova E não selecionou uma existente, geramos um erro.
            bindingResult.rejectValue("categoria", "categoria.notnull", "É necessário selecionar uma categoria existente ou criar uma nova.");
        }
        // Se uma categoria existente foi selecionada, o th:field="*{categoria}" já a associou ao objeto produto.

        // 3. A verificação de erros agora inclui nosso erro customizado de categoria.
        if (bindingResult.hasErrors()) {
            // Se houver qualquer erro, recarregamos a lista de categorias e voltamos ao formulário.
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "produtos/add-edit";
        }
        
        // 4. Com tudo validado e a categoria corretamente associada, salvamos o produto.
        produtoRepository.save(produto);
        return "redirect:/produtos/list";
    }

    // Este método não precisa de alterações.
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de produto inválido:" + id));
        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "produtos/add-edit";
    }

    // Este método não precisa de alterações.
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteProduto(@PathVariable("id") Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos/list";
    }
}