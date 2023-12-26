package com.microservico.estoquepreco.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long id;
    private String status;
    @Min( value = 1, message="O valor do campo deve ser maior que zero.")
    private Double total;
    private List<ItemPedidoDTO> itemsPedido;
}