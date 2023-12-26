package com.microservico.estoquepreco.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservico.estoquepreco.model.*;
import com.microservico.estoquepreco.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PedidoController.class)
class PedidoControllerTest {
   // @Autowired
   // private RabbitmqService rabbitmqService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PedidoService pedidoService;

    private PedidoDTO pedido;
    private List<ItemPedidoDTO> itemsPedido;
     @BeforeEach
    public void setup() {
        PedidoDTO.PedidoDTOBuilder pedidoDTO = PedidoDTO.builder();
        pedidoDTO.id(100L);
        pedidoDTO.status("criado");
        pedidoDTO.totalItem(20D);

        List<ItemPedidoDTO> listItensDTO = new ArrayList<>(1);
        ItemPedidoDTO.ItemPedidoDTOBuilder itemPedidoDTO = ItemPedidoDTO.builder();

        itemPedidoDTO.id(1L);
        itemPedidoDTO.produtoId(1L);
        itemPedidoDTO.quantidade(5);
        itemPedidoDTO.preco(10D);
        listItensDTO.add(itemPedidoDTO.build());
        pedidoDTO.itemsPedido(listItensDTO);
        pedido = pedidoDTO.build();
    }

    @DisplayName("testGivenPedidoObject_WhenCreatePedido_ThenReturnPedidoObject")
    @Test
    void testGivenPedidoObject_WhenCreatePedido_ThenReturnPedidoObject() throws Exception {
         given(pedidoService.create(any(PedidoDTO.class)))
                 .willAnswer( (invocation) -> invocation.getArgument(0) );

         ResultActions response = mockMvc.perform(post("/pedido")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(pedido)));

         response.andDo(print())
                 .andExpect(status().isOk())
                 .andExpect( jsonPath("$.id", is(pedido.getId() )));
    }


}
