package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import jakarta.validation.constraints.NotNull;

public record AlterarPedidoDto(
        @NotNull
        StatusPedidoEnum status
) {
}