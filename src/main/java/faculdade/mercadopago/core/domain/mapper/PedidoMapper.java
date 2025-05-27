package faculdade.mercadopago.core.domain.mapper;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "pedido.status", target = "pedido")
    static ViewPedidoDto entityToDto(PedidoEntity produtoEntity);

    @Mapping(source = "status", target = "status")
    void updateFromDto(ViewPedidoDto dto, @MappingTarget PedidoEntity entity);
}
