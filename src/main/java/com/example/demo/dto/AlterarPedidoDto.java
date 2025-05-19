package com.example.demo.dto;

import com.example.demo.enums.StatusPedidoEnum;

public record AlterarPedidoDto(
        StatusPedidoEnum status
) {
}
