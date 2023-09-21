package com.softexpert.desafioBackendSE.api.repository;

import com.softexpert.desafioBackendSE.api.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Integer> {
}
