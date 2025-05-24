package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record EntregaDto(
        @NotNull
        Long pedidocodigo,

        @NotNull
        Date datahoraentrega
) {
}
