package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewFilaDto {

    private long codigo;
    private long codigoPedido;

}
