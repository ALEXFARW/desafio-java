package com.microservico.estoquepreco.service;

import com.microservico.estoquepreco.exceptions.BusinessException;
import com.microservico.estoquepreco.model.Produto;
import com.microservico.estoquepreco.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static java.lang.String.format;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;

    public Produto create(Produto produto) {
        List<Produto> savedBook = produtoRepository.findByNome(produto.getNome() );
        if (!savedBook.isEmpty()) {
           throw new BusinessException(format("Existe um produto(%s) com este nome na tabela de produtos", produto.getNome()));
        }
        return produtoRepository.save(produto);
    }
    public Produto update( Produto produto) {
        var entity = produtoRepository.findById(produto.getId())
                .orElseThrow( ()  -> new BusinessException("Produto não encontrado. Identificador: "+ produto.getId() ));
        entity.setNome( produto.getNome());
        entity.setPreco(produto.getPreco());
        entity.setCategoria(produto.getCategoria());
        return produtoRepository.save(entity);
    }
    public void delete(Long id) {
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        format("Produto %s não encontrado.", id)));
        produtoRepository.delete(entity);
    }
    public Optional<Produto> findById(Long id) {
        return Optional.ofNullable(produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                         format("Produto %s não encontrado.", id))));
    }

    public List<Produto> ListaProdutos() {
        return produtoRepository.findAll();
    }
}
