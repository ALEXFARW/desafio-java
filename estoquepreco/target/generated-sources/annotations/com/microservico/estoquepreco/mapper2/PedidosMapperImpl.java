package com.microservico.estoquepreco.mapper2;

import com.microservico.estoquepreco.model.Pedido;
import com.microservico.estoquepreco.model.PedidoDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-26T08:37:06-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PedidosMapperImpl implements PedidosMapper {

    @Override
    public PedidoDTO toDTO(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoDTO pedidoDTO = new PedidoDTO();

        return pedidoDTO;
    }
}
