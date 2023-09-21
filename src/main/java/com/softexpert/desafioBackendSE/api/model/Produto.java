package com.softexpert.desafioBackendSE.api.model;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CD_PRODUTO")
    private Integer cdProduto;

    @Column(name = "TX_NOME")
    private String txNome;

    @Column(name = "TX_PRECO")
    private Double preco;
}
