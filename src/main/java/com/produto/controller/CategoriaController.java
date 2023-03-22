package com.produto.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.produto.model.Categoria;
import com.produto.model.Produto;
import com.produto.repository.CategoriaRepository;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping("/novo")
	public String adicionarCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "publico-criar-categoria";
	}
	
	@PostMapping("/salvar")
	public String salvarProduto(@Valid Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return "/publico-criar-categoria";
		}
		
		categoriaRepository.save(categoria);
		attributes.addFlashAttribute("mensagem", "Categoria cadastrada com sucesso");
		return "redirect:/categoria/novo";
		
	}
	
	@GetMapping("/lista")
	public String listarCategorias(Model model) {
		
		model.addAttribute("listacategorias", categoriaRepository.findAll());
		return "/publico-lista-categorias";
		
	}
	
	@GetMapping("/apagar/{id}")
	public String apagarCategoria(@PathVariable("id") Long id, Model model) {
		Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID Inválido" + id));
		
		categoriaRepository.delete(categoria);
		return "redirect:/categoria/lista";
	
	}
	
	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, Model model) {
		Optional<Categoria> categoriaVelha = categoriaRepository.findById(id);
		if (!categoriaVelha.isPresent()) {
			throw new IllegalArgumentException("Categoria inválida: " + id);
		}
		
		Categoria categoria = categoriaVelha.get();
		model.addAttribute("categoria", categoria);
		return "/publico-editar-categoria";
		
	}
	
	@PostMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, @Valid Categoria categoria, BindingResult result) {
		
		if (result.hasErrors()) {
			categoria.setId(id);
			return "/publico-editar-categoria";
			
		}
		
		categoriaRepository.save(categoria);
		return "redirect:/categoria/lista";
		
		
	}
	
}
