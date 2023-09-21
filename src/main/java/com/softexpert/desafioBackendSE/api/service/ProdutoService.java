package com.softexpert.desafioBackendSE.api.service;

import com.softexpert.desafioBackendSE.api.model.Produto;
import com.softexpert.desafioBackendSE.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Optional<Produto> findById(Integer id) {
        Optional<Produto> produto =  produtoRepository.findById(id);
        return produto;
    }
}
