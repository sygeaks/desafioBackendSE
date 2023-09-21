package com.softexpert.desafioBackendSE.api.controller;

import com.softexpert.desafioBackendSE.api.dto.PedidoRequestDTO;
import com.softexpert.desafioBackendSE.api.dto.PedidoResponseDTO;
import com.softexpert.desafioBackendSE.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> realizarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        return new ResponseEntity<>(pedidoService
                .calculoPagmantoPorPessoa(pedidoRequestDTO), HttpStatus.CREATED);
    }
}
