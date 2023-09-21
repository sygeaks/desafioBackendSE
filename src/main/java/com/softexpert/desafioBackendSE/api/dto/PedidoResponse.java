package com.softexpert.desafioBackendSE.api.dto;

import lombok.Data;

@Data
public class PedidoResponse {

    private String nomeIntegrantePedido;
    private Double valorPagamento;
    private String paymentUrl;

}
