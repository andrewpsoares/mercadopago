package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewFilaDto {

    private StatusPedidoEnum status;
    private long codigoPedido;

}
