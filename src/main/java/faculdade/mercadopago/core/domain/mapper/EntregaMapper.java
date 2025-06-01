package faculdade.mercadopago.core.domain.mapper;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.core.domain.dto.ViewEntregaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntregaMapper {
    @Mapping(source = "dataHoraEntrega", target = "DataHoraEntrega")
    @Mapping(source = "pedidoCodigo.status", target = "status")
    ViewEntregaDto entityToDto(EntregaEntity entrega);
}
