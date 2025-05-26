package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewEntregaDto {
    private long codigo;
    private StatusPedidoEnum status;
    private Date DataHoraEntrega;
}