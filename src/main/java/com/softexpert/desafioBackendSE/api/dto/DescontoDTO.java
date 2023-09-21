package com.softexpert.desafioBackendSE.api.dto;

import com.softexpert.desafioBackendSE.api.enums.TipoDescontoEnum;
import lombok.Data;

@Data
public class DescontoDTO {


    private Double valor;
    private TipoDescontoEnum tipo;
}
