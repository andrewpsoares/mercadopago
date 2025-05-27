package faculdade.mercadopago.core.domain.mapper;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "codigo", target = "pedido")
    ViewPedidoDto entityToDto(PedidoEntity pedidoEntity);

    default long map(UsuarioEntity usuarioEntity) {
        return usuarioEntity.getCodigo();
    }
}
