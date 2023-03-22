package com.produto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("mensagem", "Bem vindo");
		return "publico-index";
		
	}
	
}
