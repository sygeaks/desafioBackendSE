package com.softexpert.desafioBackendSE.api.dto;

import lombok.Data;

@Data
public class PagamentoResponseDTO {

    private String referenceId;
    private String paymentUrl;
    private Qrcode qrcode;
}
