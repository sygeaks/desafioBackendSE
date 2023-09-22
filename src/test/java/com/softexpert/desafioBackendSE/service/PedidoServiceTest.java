package com.softexpert.desafioBackendSE.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softexpert.desafioBackendSE.api.client.PagamentoClient;
import com.softexpert.desafioBackendSE.api.dto.*;
import com.softexpert.desafioBackendSE.api.enums.TipoDescontoEnum;
import com.softexpert.desafioBackendSE.api.exception.BusinessException;
import com.softexpert.desafioBackendSE.api.model.Produto;
import com.softexpert.desafioBackendSE.api.repository.ProdutoRepository;
import com.softexpert.desafioBackendSE.api.service.PedidoService;
import com.softexpert.desafioBackendSE.api.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;


@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {


    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PagamentoClient pagamentoClient;

    @Mock
    private ObjectMapper objectMapper;


    @Test
    public void recuperaProdutoTest() {

        Integer codigoProduto = 123;
        Produto produtoEsperado = new Produto();

        Mockito.when(produtoService.findById(codigoProduto)).thenReturn(Optional.of(produtoEsperado));

        Produto produtoRetornado = pedidoService.recuperaProduto(codigoProduto);
        Assertions.assertNotNull(produtoRetornado);
        Assertions.assertEquals(produtoEsperado, produtoRetornado);
    }

    @Test
    public void recuperaProdutoNaoEncontradoTest() {

        Integer codigoProduto = 456;

        Mockito.when(produtoService.findById(codigoProduto)).thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            pedidoService.recuperaProduto(codigoProduto);
        });

        Assertions.assertEquals("Produto não encontrado. Codigo: " + codigoProduto, exception.getMessage());
    }

    @Test
    void calcularTaxasTest(){

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        List<TaxaDTO> taxas = new ArrayList<>();
        TaxaDTO taxa = new TaxaDTO();
        taxa.setValor(new Double(1.0));
        taxas.add(taxa);
        pedidoRequestDTO.setTaxa(taxas);

        double valorTaxa = pedidoService.calcularTaxas(pedidoRequestDTO);

        Assertions.assertNotNull(valorTaxa);
    }


    @Test
    void calcularValorDescontoComTipoPercentualTest(){

        double totalPedido = 1.1;

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        List<DescontoDTO> descontos = new ArrayList<>();
        DescontoDTO desconto = new DescontoDTO();
        desconto.setValor(1.2);
        desconto.setTipo(TipoDescontoEnum.PERCENTUAL);
        descontos.add(desconto);
        pedidoRequestDTO.setDesconto(descontos);

        double valorDesconto = pedidoService.calcularValorDesconto( pedidoRequestDTO,  totalPedido);

        Assertions.assertNotNull(valorDesconto);

    }


    @Test
    void dividePagamentoComvalorIndividual_valorTotal_valorDevidoNaoNullTest(){

        double valorIndividual = 1.2;
        double valorTotal = 1.5;
        double valorDevido = 2.3;

        double valorVivisao = pedidoService.dividePagamento(valorIndividual, valorTotal, valorDevido);

        Assertions.assertNotNull(valorVivisao);

   }

   @Test
   void calculaValorDescontoComTipoDescontoPercentualTest(){

       DescontoDTO desconto = new DescontoDTO();
       desconto.setTipo(TipoDescontoEnum.PERCENTUAL);
       desconto.setValor(1.4);

       Double valorTotal = 12.56;

      double  valorDesconto =  pedidoService.calculaValorDesconto( desconto,  valorTotal);

      Assertions.assertNotNull(valorDesconto);

   }

    @Test
    void calculaValorDescontoComTipoDescontoValorRealTest(){

        DescontoDTO desconto = new DescontoDTO();
        desconto.setTipo(TipoDescontoEnum.VALOR_REAL);
        desconto.setValor(20.0);

        Double valorTotal = 12.56;

        double  valorDesconto =  pedidoService.calculaValorDesconto( desconto,  valorTotal);

        Assertions.assertNotNull(valorDesconto);
        Assertions.assertEquals(valorDesconto, 20.0);

    }

    @Test
    void getLinkPagamentoTest(){

        String result = "{\"referenceId\": \"102039\",\"paymentUrl\": \"https://app.picpay.com/checkout/NWZkOTFjZTA4.....ZWJmM2QxMzA2\"}";
        String token = "123";
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        Mockito.when(pagamentoClient.payments(Mockito.any(),Mockito.any(PagamentoRequest.class))).thenReturn(result);
        pedidoService.setObjectMapper(new ObjectMapper());
        PagamentoResponseDTO responseDTO = pedidoService.getLinkPagamento(pagamentoRequest);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals("102039", responseDTO.getReferenceId());
    }

    void Teste2(){
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        PedidoDTO pedidoDTO = new PedidoDTO(21, "Marcio");
        TaxaDTO taxaDTO = new TaxaDTO(1.2);
        DescontoDTO descontoDTO = new DescontoDTO(1.3,TipoDescontoEnum.PERCENTUAL);
        pedidoRequestDTO.setPedido(Arrays.asList(pedidoDTO));
        pedidoRequestDTO.setTaxa(Arrays.asList(taxaDTO));
        pedidoRequestDTO.setDesconto(Arrays.asList(descontoDTO));

        PedidoResponseDTO responseDTO = pedidoService.gerarDistribuicaoValores( pedidoRequestDTO);

        Assertions.assertNotNull(responseDTO);
    }


    @Test
   void calculaValorIndividualTest() {
        List<Produto> produtos = Arrays.asList(
                new Produto(21, "Produto1", 10.0),
                new Produto(22, "Produto2", 20.0),
                new Produto(23, "Produto3", 30.0)
        );
        Map.Entry<String, List<Produto>> entry = new AbstractMap.SimpleEntry<>("Chave", produtos);

        Double atual = pedidoService.calculaValorIndividual(entry);
        Double esperado = 10.0 + 20.0 + 30.0;
        Assertions.assertEquals(esperado, atual);
    }

    @Test
   void calcularValorTotalPedidoCompraTest() {

        Map<String, List<Produto>> produtosPorIntegrante = new HashMap<>();
        produtosPorIntegrante.put("Integrante1", Arrays.asList(new Produto(21,"Produto1", 10.0), new Produto(23,"Produto3", 20.0)));
        produtosPorIntegrante.put("Integrante2", Arrays.asList(new Produto(22,"Produto2", 15.0), new Produto(21, "Produto1", 25.0)));

        Double esperado = 10.0 + 20.0 + 15.0 + 25.0;

        Double totalPedidoAtual = pedidoService.calcularValorTotalPedidoCompra(produtosPorIntegrante);
        Assertions.assertEquals(esperado, totalPedidoAtual);
    }

}
