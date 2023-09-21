package com.softexpert.desafioBackendSE.api.repository;

import com.softexpert.desafioBackendSE.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
