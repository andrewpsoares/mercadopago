package faculdade.mercadopago.core.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.util.List;

public record NewPedidoDto(

        @NotNull
        Long usuariocodigo,

        @NotNull
        Time tempototalpreparo,

        @Size(min = 1)
        @NotNull
        @Valid
        List<NewPedidoItemDto> itens
) {
}

