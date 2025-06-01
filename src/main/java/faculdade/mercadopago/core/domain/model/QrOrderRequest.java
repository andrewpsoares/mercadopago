package faculdade.mercadopago.core.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrOrderRequest {
    @JsonProperty("external_reference")
    private String ExternalReference;

    @JsonProperty("title")
    private String Title;

    @JsonProperty("description")
    private String Description;

    @JsonProperty("total_amount")
    private Double TotalAmount;

    @JsonProperty("notification_url")
    private String NotificationUrl;

    @JsonProperty("items")
    private List<ItemRequest> Items;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ItemRequest {
        @JsonProperty("sku_number")
        private String SkuNumber;

        @JsonProperty("category")
        private String Category;

        @JsonProperty("title")
        private String Title;

        @JsonProperty("description")
        private String Description;

        @JsonProperty("quantity")
        private Integer Quantity;

        @JsonProperty("unit_measure")
        private String UnitMeasure;

        @JsonProperty("unit_price")
        private BigDecimal UnitPrice;

        @JsonProperty("total_amount")
        private BigDecimal TotalAmount;
    }
}
