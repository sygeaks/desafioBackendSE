package com.softexpert.desafioBackendSE.api.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tab_pedido_item")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tbl_order_item_id")
    private Integer id;

    @Column
    private Double ingredientPrice;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tbl_order_id", nullable = false)
    private Pedido pedido;
}
