package faculdade.mercadopago.core.domain.dto;

import lombok.Data;

@Data
public class NewPaymentDto {
    public long OrderId;
    public double TotalAmount;
}
