package faculdade.mercadopago.core.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class NewPaymentDto {
    public Long OrderId;
    public double TotalAmount;
    public List<ItemPedidoDto> Itens;

    @Data
    public static class ItemPedidoDto {
        public Long Codigo;
        public Integer quantidade;
        public BigDecimal Valor;
    }
}
