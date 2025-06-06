package faculdade.mercadopago.core.domain.model;

import lombok.Data;

@Data
public class QrOrderPagamentoResponse {
    private String id;
    private Boolean live_mode;
    private String type;
    private String date_created;
    private Long user_id;
    private String api_version;
    private String action;
    private PaymentData data;

    @Data
    public static class PaymentData {
        private Long id;
    }
}
