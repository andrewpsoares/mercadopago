package com.example.demo.core.domain.dto;

import com.example.demo.core.domain.enums.StatusPedidoEnum;
import jakarta.validation.constraints.NotNull;

public record AlterarPedidoDto(
        @NotNull
        StatusPedidoEnum status
) {
}
