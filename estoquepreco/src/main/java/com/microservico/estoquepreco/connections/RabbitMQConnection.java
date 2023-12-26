package com.microservico.estoquepreco.connections;

import constantes.RabbitmqConstantes;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {
    private AmqpAdmin amqpAdmin;
    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
      this.amqpAdmin = amqpAdmin;
    }

    private static final String NOME_EXCHANGE = "amq.direct";
    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }
    private DirectExchange trocaDireta() {
        return new DirectExchange(NOME_EXCHANGE);
    }
    private Binding relacionamento(Queue fila, DirectExchange troca) {
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona() {
        Queue filaEstoque = this.fila(RabbitmqConstantes.FILA_ESTOQUE);
        Queue filaPreco = this.fila(RabbitmqConstantes.FILA_PRECO);
        //Queue filaPedido = this.fila(RabbitmqConstantes.FILA_PEDIDO;
        //encaminha para fila
        DirectExchange troca = this.trocaDireta();
        //relacionamento
        Binding ligacaoEstoque = this.relacionamento(filaEstoque,troca);
        Binding ligacaoPreco = this.relacionamento(filaPreco,troca);
        //criando as filas no rabbitMQ
        this.amqpAdmin.declareQueue(filaEstoque);
        this.amqpAdmin.declareQueue(filaPreco);
        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoEstoque);
        this.amqpAdmin.declareBinding(ligacaoPreco);
    }
}
