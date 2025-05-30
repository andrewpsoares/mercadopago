package faculdade.mercadopago.core.domain.mapper;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.core.domain.dto.ViewFilaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilaPedidosPreparacaoMapper {
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "pedidocodigo.codigo", target = "codigoPedido")
    ViewFilaDto entityToDto(FilaPedidosPreparacaoEntity pedidoFila);
}
