package com.manager.stock_manager.controller; // Pacote ajustado

import com.manager.stock_manager.model.Categoria; // Importação ajustada
import com.manager.stock_manager.model.Produto; // Importação ajustada
import com.manager.stock_manager.repository.CategoriaRepository; // Importação ajustada
import com.manager.stock_manager.repository.ProdutoRepository; // Importação ajustada
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

    @GetMapping("/list")
    public String listProdutos(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtos);
        return "produtos/list";
    }

    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode acessar
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "produtos/add-edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String saveProduto(@Valid @ModelAttribute("produto") Produto produto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "produtos/add-edit";
        }
        produtoRepository.save(produto);
        return "redirect:/produtos/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de produto inválido:" + id));
        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "produtos/add-edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteProduto(@PathVariable("id") Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos/list";
    }
}