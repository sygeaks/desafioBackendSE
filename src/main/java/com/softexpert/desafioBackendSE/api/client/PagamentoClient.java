package com.softexpert.desafioBackendSE.api.client;

import com.softexpert.desafioBackendSE.api.dto.PagamentoResponseDTO;
import com.softexpert.desafioBackendSE.api.dto.PagamentoRequest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@FeignClient(value = "pagamentoClient", url = "${service.pic-pay.base-url}")
public interface PagamentoClient {
    @PostMapping("${service.pic-pay.ecommerce}")
    PagamentoResponseDTO payments(@RequestHeader("x-picpay-token") String picpayToken,
                                   @RequestBody PagamentoRequest requestDTO);
}
