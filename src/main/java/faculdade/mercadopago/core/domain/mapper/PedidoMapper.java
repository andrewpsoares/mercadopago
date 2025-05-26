package faculdade.mercadopago.core.domain.mapper;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    @Mapping(source = "pedido.status", target = "pedido")
     ViewPedidoDto entityToDto(PedidoEntity produtoEntity);
}
