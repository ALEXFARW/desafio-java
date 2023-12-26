package com.microservico.estoquepreco.controller;

import com.microservico.estoquepreco.repository.PedidoRepository;
import constantes.RabbitmqConstantes;
import dto.EstoqueDto;
import com.microservico.estoquepreco.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "estoque")
public class EstoqueController {

    @Autowired
    private RabbitmqService rabbitmqService;

    @Autowired
    private PedidoRepository pedidoRepository;
    @PutMapping
    private ResponseEntity alteraEstoque(@RequestBody EstoqueDto estoqueDto) {
        this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_ESTOQUE, estoqueDto);
        System.out.println(estoqueDto.codigoproduto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
