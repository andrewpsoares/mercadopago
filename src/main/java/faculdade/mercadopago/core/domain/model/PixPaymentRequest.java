package faculdade.mercadopago.core.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PixPaymentRequest {
    @JsonProperty("transaction_amount")
    private double TransactionAmount;

    @JsonProperty("payment_method_id")
    private String PaymentMethodId;

    @JsonProperty("description")
    private String Description;

    @JsonProperty("external_reference")
    private String ExternalReference;

    @JsonProperty("installments")
    private int Installments;

    @JsonProperty("payer")
    private Payer Payer;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payer {
        @JsonProperty("first_name")
        private String FirstName;

        @JsonProperty("last_name")
        private String LastName;

        @JsonProperty("email")
        private String Email;

        @JsonProperty("identification")
        private Identification Identification;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Identification {
        @JsonProperty("number")
        private String Number;

        @JsonProperty("type")
        private String Type;
    }
}
