package faculdade.mercadopago.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Item> Items;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Item {
        @JsonProperty("sku_number")
        private String SkuNumber;

        @JsonProperty("category")
        private String Category;

        @JsonProperty("title")
        private String Title;

        @JsonProperty("description")
        private String Description;

        @JsonProperty("quantity")
        private Double Quantity;

        @JsonProperty("unit_measure")
        private String UnitMeasure;

        @JsonProperty("unit_price")
        private Double UnitPrice;

        @JsonProperty("total_amount")
        private Double TotalAmount;
    }
}
