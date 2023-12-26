package com.microservico.estoquepreco.controller;

import com.microservico.estoquepreco.exceptions.BusinessException;
import com.microservico.estoquepreco.model.*;
import com.microservico.estoquepreco.service.PedidoService;
import com.microservico.estoquepreco.service.RabbitmqService;
import constantes.RabbitmqConstantes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "pedido")
public class PedidoController {
    //@Autowired
    //private RabbitmqService rabbitmqService;
    @Autowired
    private PedidoService pedidoService;
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @Operation(
            summary = "Cadastrar um novo pedido",
            description = "Cadastrar um produto (nome, preço, categoria).",
            tags = { "Cadastro de produtos" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(description = "Existe um produto(%s) com este nome na tabela de produtos",
                            responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity criaPedido(@RequestBody @Valid PedidoDTO pedidoDTO) {
//        StringBuilder errorMessage = new StringBuilder();
//        for ( ItemPedidoDTO item: pedidoDTO.getItemsPedido() ) {
//            System.out.println("produto:" +item.getProdutoId());
//            if (item.getProdutoId() == null)
//                errorMessage.append("  Produto precisa ter um valor válido (número inteiro)");
//            else
//              if (!pedidoService.produtoExiste(item.getProdutoId()))
//                errorMessage.append( format("Produto %s não encontrado na tabela de produtos ",item.getProdutoId()) );
//
//            if (item.getQuantidade() == null | item.getQuantidade() < 1D)
//                errorMessage.append("  Quantidade precisa ter um valor inteiro maior que zero");
//            if (item.getPreco() == null | item.getPreco() <= 0D)
//                errorMessage.append("  Preço precisa ter um valor maior que zero");
//        }
//        if (!errorMessage.isEmpty()) {
//            throw new InvalidParametersException("Erro(s) ocorrido(s) ao gerar pedido "+ errorMessage);
//        }


        try {
            Pedido pedido =pedidoService.create(pedidoDTO);
            System.out.println(pedido.getId());
        } catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }

     //   this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_ESTOQUE, pedidoDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Atualizar um novo pedido",
            description = "Atualizar um produto (alterar a quantidade, adicionar/remover um item).",
            tags = { "Cadastro de produtos" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(description = "Existe um produto(%s) com este nome na tabela de produtos",
                            responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    private ResponseEntity alteraPedido(@RequestBody PedidoDTO pedidoDTO) {
        try {
            return ResponseEntity.ok(pedidoService.update(pedidoDTO));
        } catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }

//        this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_ESTOQUE, pedidoDTO);
//        System.out.println(pedido.getId());
//        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Pesquisar um produto",
            description = "Pesquisar um produto pelo seu identificador.",
            tags = { "Cadastro de produtos" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(description = "Produto não encontrado para o identificador", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Optional<PedidoDTO>> findById(@PathVariable("id")
             @Parameter(description = "The Identificador do pedido.") Long id) {
        try {
            //retornar o pedido e seus itens
            return ResponseEntity.ok(pedidoService.findById(id));
        }
        catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os produtos cadastrados",
            description = "Lista todos os produtos cadastrados na tabela de produto.",
            tags = { "Cadastro de Produtos" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public List<PedidoDTO> findAll() {
        return pedidoService.ListaPedidos();
    }

}
