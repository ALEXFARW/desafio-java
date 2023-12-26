package com.microservico.estoquepreco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "itempedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long produtoId;

    @Column(nullable = false)
    //@Min( value = 1, message="O valor do campo quantidade deve ser maior que zero.")
    private Integer quantidade;

    @Column(nullable = false)
    //@Min( value = 1, message="O valor do campo preço deve ser maior que zero.")
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public ItemPedido(ItemPedidoDTO data){
        this.id = data.getId();
        this.produtoId = data.getProdutoId();
        this.preco = data.getPreco();
        this.quantidade = data.getQuantidade();
        if (data.getPedido_id() != null)
           this.pedido.setId(data.getPedido_id());
    }
}
