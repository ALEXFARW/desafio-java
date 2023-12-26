package com.microservico.estoquepreco.repository;

import static org.junit.jupiter.api.Assertions.*;
import com.microservico.estoquepreco.model.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class ProdutoRepositoryTest {
    @Autowired
    private ProdutoRepository produtoRepository;
    @DisplayName("test GivenProdutoObject_WhenSaveProduto_ThenReturnSavedProduto")
    @Test
    void testGivenProdutoObject_WhenSaveProduto_ThenReturnSavedProduto() {
        Produto produto = new Produto(1L, "Coca cola lata 330ml", 3D, "Bebida");
        Produto savedProduto =produtoRepository.save(produto);
        assertNotNull(savedProduto);
        assertTrue(savedProduto.getId()  > 0);
        assertNotNull( savedProduto.getCategoria());
        assertNotNull( savedProduto.getNome());
        assertTrue(savedProduto.getPreco()  > 0);
    }
    @DisplayName("test GivenProdutoObject_WhenSaveProduto_ThenReturnSavedProduto")
    @Test
    void testGivenProdutoList_WhenFindAllProduto_ThenReturnProdutoList() {
        Produto produto1 = new Produto(1L, "Coca cola lata 330ml", 3D, "Bebida");
        Produto produto2 = new Produto(2L, "Coca cola retornável 1Lt", 7D, "Bebida");
        Produto produto3 = new Produto(3L, "Coca cola garrafa 600ml", 5D, "Bebida");
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);
        produtoRepository.save(produto3);

        List<Produto> produtoList = produtoRepository.findAll();
        assertNotNull( produtoList);
        assertEquals(3, produtoList.size());
    }

}
