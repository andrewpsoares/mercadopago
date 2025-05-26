package faculdade.mercadopago.core.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CriarPedidoDto(

        @NotNull
        Long usuariocodigo,

        @Size(min = 1)
        @NotNull
        @Valid
        List<CriarPedidoItemDto> itens
) {
}

