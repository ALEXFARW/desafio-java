package com.microservico.estoquepreco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[A-Z]+(.)*" , message="O campo nome deve começar com uma letra maiúscula")
    @NotBlank(message="Informe um valor para o campo nome")
    @Column(nullable = false)
    private String nome;
    @Min( value = 1, message="O valor do campo preço deve ser maior que zero.")
    @Column(nullable = false)
    private Double preco;
    @NotBlank(message="Informe um valor para o campo categoria")
    @Column(nullable = false)
    private String categoria;

//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
//    private List<ItemPedido> itemPedidoList = new ArrayList<>();
    public Produto(ProdutoDTO data){
        this.id = data.id();
        this.nome = data.nome();
        this.preco = data.preco();
        this.categoria = data.categoria();
    }
}
