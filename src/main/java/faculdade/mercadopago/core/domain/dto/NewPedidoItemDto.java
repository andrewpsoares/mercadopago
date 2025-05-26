package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.adapter.driven.entity.PedidoItemEntity;

public record NewPedidoItemDto(
        Long produtocodigo,
        int quantidade
) {
    public static NewPedidoItemDto fromEntity(PedidoItemEntity dados) {
        return new NewPedidoItemDto(
                dados.getCodigo(),
                dados.getQuantidade()
        );
    }
}
