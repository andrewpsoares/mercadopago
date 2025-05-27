package faculdade.mercadopago.core.domain.dto;

import lombok.Data;

@Data
public class NewPedidoItemDto {

    private long produtocodigo;
    private int quantidade;
}
