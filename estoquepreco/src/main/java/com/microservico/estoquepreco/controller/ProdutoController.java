package com.microservico.estoquepreco.controller;

import com.microservico.estoquepreco.exceptions.BusinessException;
import com.microservico.estoquepreco.model.Produto;
import com.microservico.estoquepreco.repository.PedidoRepository;
import com.microservico.estoquepreco.service.ProdutoService;
import com.microservico.estoquepreco.service.RabbitmqService;
import constantes.RabbitmqConstantes;
import dto.EstoqueDto;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

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
            summary = "Cadastrar um produto",
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
                            responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Produto> create(@RequestBody @Valid Produto produto) {
        try {
            return ResponseEntity.ok(produtoService.create(produto));
        } catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Produto> save(@RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(produtoService.update(produto));
        } catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }
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
    public ResponseEntity<Optional<Produto>>
    findById(@PathVariable("id")
             @Parameter(description = "The Id of the book to find.") Long id) {
        try {
            return ResponseEntity.ok(produtoService.findById(id));
        }
        catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }
    }

    @Operation(
            summary = "Deleta um produto",
            description = "Deleta um produto pelo seu identificador.",
            tags = { "Cadastro de Produtos" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id")
                                    @Parameter(description = "O Identificador do produto.")
                                    Long id) {
        try {
            produtoService.delete(id);
            return ResponseEntity.noContent().build();
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
    public List<Produto> findAll() {
        return produtoService.ListaProdutos();
    }
}
