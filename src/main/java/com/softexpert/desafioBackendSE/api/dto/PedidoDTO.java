package com.softexpert.desafioBackendSE.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {

    private Integer codigoProduto;
    private String nomeIntegrantePedido;

}
