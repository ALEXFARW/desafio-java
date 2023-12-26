package com.microservico.estoquepreco.repository;

import com.microservico.estoquepreco.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    ItemPedido findByProdutoId(Long productId);

    //Boolean findByProdutoId(Long productId);

    @Query(value="select * from itempedido p where p.pedido_id = :pedido_id and p.produto_id  = :produto_id", nativeQuery = true )
    Optional<ItemPedido> findProductItem(Long pedido_id, Long produto_id);

    @Query(value="select sum(preco * quantidade) totalItem from itempedido p where p.pedido_id = :pedido_id", nativeQuery = true )
    Double somaItensPedido(Long pedido_id);
}
