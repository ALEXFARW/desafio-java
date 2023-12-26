package com.microservico.estoquepreco.repository;

import com.microservico.estoquepreco.model.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;
    @DisplayName("test GivenPedidoObject_WhenSavePedido_ThenReturnSavedPedido")
    @Test
    void testGivenPedidoObject_WhenSavePedido_ThenReturnSavedPedido() {
        //
    }

}
