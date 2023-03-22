package com.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produto.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	
	
}
