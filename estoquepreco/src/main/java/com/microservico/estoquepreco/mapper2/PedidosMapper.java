package com.microservico.estoquepreco.mapper2;

import com.microservico.estoquepreco.model.Pedido;
import com.microservico.estoquepreco.model.PedidoDTO;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;

//@Mapper(componentModel = "spring")
public interface PedidosMapper {
    //Pedido toModel(PedidoDTO pedidoDTO);

    PedidoDTO toDTO(Pedido pedido);
}
