package faculdade.pagamento.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrOrderRequest {
    @JsonProperty("external_reference")
    private String ExternalReference;
    @JsonProperty("title")
    private String Title;
    @JsonProperty("total_amount")
    private Double totalAmount;
    @JsonProperty("notification_url")
    private String NotificationUrl;
}
