package com.softexpert.desafioBackendSE.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    private List<PedidoDTO> pedido;

    private List<TaxaDTO>taxa;

    private List<DescontoDTO> desconto;
}
