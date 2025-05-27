package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewPedidoDto {
    private long pedido;
    private long usuario;
    private StatusPedidoEnum status;
    private BigDecimal valorTotal;
    private LocalDateTime dataHoraSolicitacao;
    private Time tempoTotalPreparo;
}
