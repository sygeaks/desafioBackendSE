package com.softexpert.desafioBackendSE.api.service;

import com.softexpert.desafioBackendSE.api.client.PagamentoClient;
import com.softexpert.desafioBackendSE.api.dto.*;
import com.softexpert.desafioBackendSE.api.enums.TipoDescontoEnum;
import com.softexpert.desafioBackendSE.api.exception.BusinessException;
import com.softexpert.desafioBackendSE.api.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class PedidoService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PagamentoClient pagamentoClient;


    public PedidoResponseDTO calculoPagmantoPorPessoa(PedidoRequestDTO pedidoRequestDTO) {
        if(Objects.isNull(pedidoRequestDTO))
            throw new BusinessException("Pedido inválido!");
        return gerarDistribuicaoValores(pedidoRequestDTO);
    }

    public PedidoResponseDTO gerarDistribuicaoValores(PedidoRequestDTO pedidoRequestDTO){

        Map<String, List<Produto>> produtosPorIntegrante =  this.receperarProdutosPorIntegrante(pedidoRequestDTO);

        Double totalPedido = this.calcularValorTotalPedidoCompra(produtosPorIntegrante);

        Double valorDesconto = this.calcularValorDesconto(pedidoRequestDTO, totalPedido);

        Double valorTaxas =  this.calcularTaxas(pedidoRequestDTO);

        Double totalPagamento = totalPedido + valorTaxas - valorDesconto;

        List<PedidoResponse> pedidosResponse = produtosPorIntegrante.entrySet()
                .stream()
                .map(entry -> {

                    double valorIndividual = this.calculaValorIndividual(entry);

                    PedidoResponse pedidoResponse = new PedidoResponse();
                    pedidoResponse.setNomeIntegrantePedido(entry.getKey());
                    pedidoResponse.setValorPagamento(
                            dividePagamento(valorIndividual, totalPedido, totalPagamento)
                    );
                    PagamentoRequest pagament = new PagamentoRequest();
                    PagamentoResponseDTO pagamentoResponseDTO = this.getLinkPagamento(pagament);
                   pedidoResponse.setPaymentUrl(pagamentoResponseDTO.getPaymentUrl());
                    return pedidoResponse;
                }).collect(toList());


        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setPedidoResponses(pedidosResponse);
        response.setValorTotalPedido(totalPagamento);

        return response;
    }

    public PagamentoResponseDTO getLinkPagamento(PagamentoRequest pagament) {
        PagamentoResponseDTO pagamentoResponseDTO = new PagamentoResponseDTO();
        try {
            pagamentoResponseDTO= pagamentoClient.payments("",pagament);
        }catch (Exception e) {
            throw new BusinessException("Error in getLinkPagamento");
        }
        return pagamentoResponseDTO;

    }

    public Double calculaValorIndividual(Map.Entry<String, List<Produto>> entry) {

        Double valorIndividual = entry.getValue()
                .stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        return valorIndividual;
    }

    public Double calcularTaxas(PedidoRequestDTO pedidoRequestDTO) {

        Double valorTaxas = pedidoRequestDTO.getTaxa()
                .stream()
                .mapToDouble(TaxaDTO::getValor)
                .sum();
        return valorTaxas;
    }

    public Double calcularValorDesconto(PedidoRequestDTO pedidoRequestDTO, double totalPedido) {

        Double valorDesconto = pedidoRequestDTO.getDesconto()
                .stream()
                .mapToDouble(desconto -> calculaValorDesconto(desconto, totalPedido))
                .sum();
        return valorDesconto;
    }

    private Double calcularValorTotalPedidoCompra(Map<String, List<Produto>> produtosPorIntegrante) {

        Double totalPedido = produtosPorIntegrante.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Produto::getPreco)
                .sum();

        return  totalPedido;
    }

    private Map<String, List<Produto>> receperarProdutosPorIntegrante(PedidoRequestDTO pedidoRequestDTO) {

        Map<String, List<Produto>> produtosPorIntegrante = pedidoRequestDTO.getPedido()
                .stream()
                .collect(
                        groupingBy(
                                PedidoDTO::getNomeIntegrantePedido,
                                mapping(pedido -> recuperaProduto(pedido.getCodigoProduto()), toList())
                        )
                );

        return  produtosPorIntegrante;

    }

    public Produto recuperaProduto(Integer codigoProduto){
        return produtoService.findById(codigoProduto)
                .orElseThrow( ()-> new BusinessException("Produto não encontrado. Codigo: "+ codigoProduto));
    }

    public Double dividePagamento(double valorIndividual, double valorTotal, double valorDevido){

       if(Objects.isNull(valorIndividual) || Objects.isNull(valorTotal) || Objects.isNull(valorDevido))
           throw new BusinessException("Divisão de pagamento não realizada!");
       return valorDevido * (valorIndividual / valorTotal);
    }


    public double calculaValorDesconto(DescontoDTO desconto, Double valorTotal){
        if(desconto.getValor() == 0) return desconto.getValor();
        return desconto.getTipo() == TipoDescontoEnum.PERCENTUAL
                ? valorTotal * (desconto.getValor() / 100)
                : desconto.getValor();
    }

}
