package com.microservico.estoquepreco.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {
   //Long id, Long produtoId, Integer quantidade, Double preco, Long pedidoId) {


    private Long id;

    @NotBlank(message="Informe um valor para o campo produto")
    private Long produtoId;
    @Min(value = 1, message = "Quantidade deve conter um valor maior que zero")
    private Integer quantidade;
    @Min(value = 1, message = "Preco deve conter um valor maior que zero")
    private Double preco;

    private Long pedido_id;

}
