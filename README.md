# desafioBackendSE

# Exemplo Teste

curl --location 'localhost:9015/pedidos' \
--header 'Content-Type: application/json' \
--data '{

    "pedido" : [
        {
             "codigoProduto": 21,
             "nomeIntegrantePedido": "Marcio"
        },
        {
             "codigoProduto": 22,
             "nomeIntegrantePedido": "Marcio"
        },
		 {
             "codigoProduto": 23,
             "nomeIntegrantePedido": "Joao"
        }
   ],
   "taxa" : [
        {
             "valor": 8
        }
   ],
   "desconto" : [
        {
             "valor": 20,
             "tipo": "VALOR_REAL"
        }
   ]

}
'

# Response

{
    "pedidoResponses": [
        {
            "nomeIntegrantePedido": "Joao",
            "valorPagamento": 6.08,
            "paymentUrl": "https://app.picpay.com/checkout/NWZkOTFjZTA4.....ZWJmM2QxMzA2"
        },
        {
            "nomeIntegrantePedido": "Marcio",
            "valorPagamento": 31.919999999999998,
            "paymentUrl": "https://app.picpay.com/checkout/NWZkOTFjZTA4.....ZWJmM2QxMzA2"
        }
    ],
    "valorTotalPedido": 38.0
}