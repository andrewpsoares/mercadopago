package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EntregaDto {
    private long codigo;
    private Long pedido;
    private StatusPedidoEnum status;
    private Date dataHoraSolicitacao;
}
