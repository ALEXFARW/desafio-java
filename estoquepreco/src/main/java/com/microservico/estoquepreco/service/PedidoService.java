package com.microservico.estoquepreco.service;

import com.microservico.estoquepreco.exceptions.BusinessException;
import com.microservico.estoquepreco.mapper2.PedidosMapper;
import com.microservico.estoquepreco.model.ItemPedido;
import com.microservico.estoquepreco.model.ItemPedidoDTO;
import com.microservico.estoquepreco.model.Pedido;
import com.microservico.estoquepreco.model.PedidoDTO;
import com.microservico.estoquepreco.repository.ItemPedidoRepository;
import com.microservico.estoquepreco.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.lang.String.format;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
//    @Autowired
//    private PedidosMapper pedidosMapper;

    public Pedido create(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(pedidoDTO);
        pedidoRepository.save(pedido);
        Double valorTotalPedido = 0D;
        List<ItemPedidoDTO> itemPedidoList = pedidoDTO.getItemsPedido();
        for (ItemPedidoDTO item : itemPedidoList) {
            System.out.println(format("item-> produtoId %s preço %s quantidade %s ",
                    item.getProdutoId(), item.getPreco(), item.getQuantidade()));
            ItemPedido itemPedido = new ItemPedido(item);
            itemPedido.setPedido(pedido);
            pedido.getItemsPedido().add(itemPedido);
            valorTotalPedido += (item.getPreco() * item.getQuantidade());
        }
        try {
            pedido.setTotal(valorTotalPedido);
            itemPedidoRepository.saveAll(pedido.getItemsPedido());
            return pedido;
        } catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }
    }

    public boolean update(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoDTO.getId()).orElseThrow();
        pedido.setStatus("alterado");
        Double valorTotalPedido = 0D;
        List<ItemPedidoDTO> itemPedidoList = pedidoDTO.getItemsPedido();
        for (ItemPedidoDTO item : itemPedidoList) {
            //check if this item exitst or not on db
            System.out.println("product id : " + item.getProdutoId());
            System.out.println("pedido id : " + item.getPedido_id());
            Optional<ItemPedido> itemPedido = itemPedidoRepository.findProductItem(item.getPedido_id(),
                    item.getProdutoId());
            valorTotalPedido += (item.getPreco() * item.getQuantidade());
            if (itemPedido.isPresent()) {
                itemPedido.get().setQuantidade(item.getQuantidade());
                itemPedido.get().setPreco(item.getPreco());
                pedido.getItemsPedido().add(itemPedido.get());
            } else {
                ItemPedido itemNovo = new ItemPedido();
                itemNovo.setProdutoId(item.getProdutoId());
                itemNovo.setQuantidade(item.getQuantidade());
                itemNovo.setPreco(item.getPreco());
                itemNovo.setPedido(pedido);
                pedido.getItemsPedido().add(itemNovo);
            }
        }
        try {
            //   Double valorTotalPedido = itemPedidoRepository.somaItensPedido(pedido.getId());
            itemPedidoRepository.saveAll(pedido.getItemsPedido());
            return true;
        } catch (BusinessException be) {
            throw new BusinessException(be.getMessage());
        }
    }

    public void delete(Long id) {
        var entity = pedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        format("Pedido %s não encontrado.", id)));
        pedidoRepository.delete(entity);
    }

    public Optional<PedidoDTO> findById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty())
            throw new BusinessException(
                    format("Pedido %s não encontrado.", id));

        PedidoDTO.PedidoDTOBuilder pedidoDTO = PedidoDTO.builder();
        pedidoDTO.id(pedido.get().getId());
        pedidoDTO.status(pedido.get().getStatus());
        pedidoDTO.total(pedido.get().getTotal());

        List<ItemPedidoDTO> listItensDTO = new ArrayList<>(1);
        ItemPedidoDTO.ItemPedidoDTOBuilder itemPedidoDTO = ItemPedidoDTO.builder();
        for (ItemPedido item : pedido.get().getItemsPedido()) {
            itemPedidoDTO.id(item.getId());
            itemPedidoDTO.produtoId(item.getProdutoId());
            itemPedidoDTO.quantidade(item.getQuantidade());
            itemPedidoDTO.preco(item.getPreco());
            listItensDTO.add(itemPedidoDTO.build());
        }
        pedidoDTO.itemsPedido(listItensDTO);
        return Optional.ofNullable(pedidoDTO.build());
    }

    public List<PedidoDTO> ListaPedidos() {
//        List<Pedido> Pedidos = pedidoRepository.findAll();
//        return Pedidos.stream()
//                .map(pedidosMapper::toDTO)
//                .collect(Collectors.toList());
        return null;
    }

    public boolean produtoExiste(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.isEmpty();
    }

}
