package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.adapter.driven.entity.PedidoItemEntity;

import java.math.BigDecimal;

public record CriarPedidoItemDto(
        Long codigo,
        Long produtocodigo,
        int quantidade,
        BigDecimal precounitario,
        BigDecimal precototal
) {
    public static CriarPedidoItemDto fromEntity(PedidoItemEntity dados) {
        return new CriarPedidoItemDto(
                dados.getCodigo(),
                dados.getProdutoCodigo(),
                dados.getQuantidade(),
                dados.getPrecoUnitario(),
                dados.getPrecoTotal()
        );
    }
}
