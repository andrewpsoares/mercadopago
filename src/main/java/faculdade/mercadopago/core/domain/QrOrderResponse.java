package faculdade.mercadopago.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QrOrderResponse {
    @JsonProperty("in_store_order_id")
    public String InStoreOrderId;

    @JsonProperty("qr_data")
    public String QrData;
}
