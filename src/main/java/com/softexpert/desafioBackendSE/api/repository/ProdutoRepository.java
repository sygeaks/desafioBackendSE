package com.softexpert.desafioBackendSE.api.repository;

import com.softexpert.desafioBackendSE.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
