package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoItemEntity;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public record CriarPedidoDto(
        Long codigo,

        @NotNull
        Long usuariocodigo,

        @NotNull
        BigDecimal valortotal,

        @NotNull
        Date datahorasolicitacao,

        @NotNull
        Time tempototalpreparo,

        @Size(min = 1)
        @NotNull
        @Valid
        List<CriarPedidoItemDto> itens
) {
}

