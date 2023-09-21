package com.softexpert.desafioBackendSE.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoResponseDTO {

    List<PedidoResponse> pedidoResponses;

    private Double valorTotalPedido;

}
