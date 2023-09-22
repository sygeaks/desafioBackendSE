package com.softexpert.desafioBackendSE.api.dto;

import com.softexpert.desafioBackendSE.api.enums.TipoDescontoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DescontoDTO {


    private Double valor;
    private TipoDescontoEnum tipo;
}
