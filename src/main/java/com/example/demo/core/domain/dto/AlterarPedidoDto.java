package com.example.demo.core.domain.dto;

import com.example.demo.core.domain.enums.StatusPedidoEnum;

public record AlterarPedidoDto(
        StatusPedidoEnum status
) {
}
