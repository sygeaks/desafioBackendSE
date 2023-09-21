package com.softexpert.desafioBackendSE.api.dto;

import lombok.Data;

@Data
public class PagamentoRequest {

    private String referenceId;
    private String callbackUrl;
    private String expiresAt;
    private String returnUrl;
    private Integer value;
    private Buyer buyer;

}
