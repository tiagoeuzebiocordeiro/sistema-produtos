package com.produto.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.produto.model.Categoria;
import com.produto.model.Produto;
import com.produto.repository.CategoriaRepository;
import com.produto.repository.ProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping("/novo")
	public String adicionarProduto(Model model) {
		model.addAttribute("produto", new Produto());
		return "/publico-criar-produto";
	}
	
	@PostMapping("/salvar")
	public String salvarProduto(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return "/publico-criar-produto";
		}
		
		produtoRepository.save(produto);
		attributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso");
		return "redirect:/produto/novo";
		
	}
	@GetMapping("/lista")
	public String listarProdutos(Model model) {
		
		model.addAttribute("listaprodutos", produtoRepository.findAll());
		return "/publico-lista-produtos";
		
	}
	
	@GetMapping("/apagar/{id}")
	public String apagarProduto(@PathVariable("id") Long id, Model model) {
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID Inválido" + id));
		
		produtoRepository.delete(produto);
		return "redirect:/produto/lista";
		
		
		
	}
	
	@GetMapping("/editar/{id}") 
	public String editarProduto(@PathVariable("id") Long id, Model model) {
		Optional<Produto> produtoVelho = produtoRepository.findById(id);
		if (!produtoVelho.isPresent()) {
			throw new IllegalArgumentException("Produto inválido: " + id);
		}

		Produto produto = produtoVelho.get();
		model.addAttribute("produto", produto);
		return "/publico-editar-produto";
	
	}
	
	@PostMapping("/editar/{id}")
	public String editarProduto(@PathVariable("id") Long id, @Valid Produto produto, BindingResult result) {
		
		if (result.hasErrors()) {
			produto.setId(id);
			return "/publico-editar-produto";
			
		}
		
		produtoRepository.save(produto);
		return "redirect:/produto/lista";
		
		
	}
	
	@ModelAttribute("categoria")
	public List<Categoria> listaDeCategorias() {
		return categoriaRepository.findAll();
	}
	
}
