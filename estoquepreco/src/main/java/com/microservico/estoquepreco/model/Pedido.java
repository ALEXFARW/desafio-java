package com.microservico.estoquepreco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

//@Table(name = "pedido")
@Entity(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min( value = 1, message="O valor do campo deve ser maior que zero.")
    private Double total;
    private String status;
    //private DateTime date;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itemsPedido = new ArrayList<>();
    public Pedido(PedidoDTO data){
        this.id = data.getId();
        this.total = data.getTotal();
        this.status = data.getStatus();
    }
}
