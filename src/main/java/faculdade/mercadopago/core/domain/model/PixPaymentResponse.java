package faculdade.mercadopago.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PixPaymentResponse {
    @JsonProperty("id")
    private Long Id;

    @JsonProperty("date_created")
    private LocalDateTime DateCreated;

    @JsonProperty("date_approved")
    private LocalDateTime DateApproved;

    @JsonProperty("date_last_updated")
    private LocalDateTime DateLastUpdated;

    @JsonProperty("money_release_date")
    private LocalDateTime MoneyReleaseDate;

    @JsonProperty("issuer_id")
    private Integer IssuerId;

    @JsonProperty("payment_method_id")
    private String PaymentMethodId;

    @JsonProperty("payment_type_id")
    private String PaymentTypeId;

    @JsonProperty("status")
    private String Status;

    @JsonProperty("status_detail")
    private String StatusDetail;

    @JsonProperty("currency_id")
    private String CurrencyId;

    @JsonProperty("description")
    private String Description;

    @JsonProperty("taxes_amount")
    private Double TaxesAmount;

    @JsonProperty("shipping_amount")
    private Double ShippingAmount;

    @JsonProperty("collector_id")
    private Long CollectorId;

    @JsonProperty("external_reference")
    private String ExternalReference;

    @JsonProperty("transaction_amount")
    private Double TransactionAmount;

    @JsonProperty("transaction_amount_refunded")
    private Double TransactionAmountRefunded;

    @JsonProperty("coupon_amount")
    private Double CouponAmount;

    @JsonProperty("statement_descriptor")
    private String StatementDescriptor;

    @JsonProperty("installments")
    private Integer Installments;

    @JsonProperty("notification_url")
    private String NotificationUrl;

    @JsonProperty("processing_mode")
    private String ProcessingMode;

    @JsonProperty("payer")
    private Payer Payer;

    @JsonProperty("metadata")
    private Map<String, Object> Metadata;

    @JsonProperty("additional_info")
    private AdditionalInfo AdditionalInfo;

    @JsonProperty("transaction_details")
    private TransactionDetails TransactionDetails;

    @JsonProperty("fee_details")
    private List<FeeDetail> FeeDetails;

    @JsonProperty("card")
    private Card Card;

    @JsonProperty("point_of_interaction")
    private PointOfInteraction PointOfInteraction;

    // ----- Inner classes -----

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payer {
        @JsonProperty("id")
        private Long Id;

        @JsonProperty("email")
        private String Email;

        @JsonProperty("type")
        private String Type;

        @JsonProperty("identification")
        private Identification Identification;
    }

    @Data
    public static class Identification {
        @JsonProperty("number")
        private Long Number;

        @JsonProperty("type")
        private String Type;
    }

    @Data
    public static class AdditionalInfo {
        @JsonProperty("items")
        private List<Item> Items;

        @JsonProperty("payer")
        private AdditionalPayer Payer;

        @JsonProperty("shipments")
        private Shipments Shipments;
    }

    @Data
    public static class Item {
        @JsonProperty("id")
        private String Id;

        @JsonProperty("title")
        private String Title;

        @JsonProperty("description")
        private String Description;

        @JsonProperty("picture_url")
        private String PictureUrl;

        @JsonProperty("category_id")
        private String CategoryId;

        @JsonProperty("quantity")
        private Integer Quantity;

        @JsonProperty("unit_price")
        private Double UnitPrice;
    }

    @Data
    public static class AdditionalPayer {
        @JsonProperty("registration_date")
        private LocalDateTime RegistrationDate;
    }

    @Data
    public static class Shipments {
        @JsonProperty("receiver_address")
        private ReceiverAddress ReceiverAddress;
    }

    @Data
    public static class ReceiverAddress {
        @JsonProperty("street_name")
        private String StreetName;

        @JsonProperty("street_number")
        private String StreetNumber;

        @JsonProperty("zip_code")
        private String ZipCode;

        @JsonProperty("city_name")
        private String CityName;

        @JsonProperty("state_name")
        private String StateName;
    }

    @Data
    public static class TransactionDetails {
        @JsonProperty("net_received_amount")
        private Double NetReceivedAmount;

        @JsonProperty("total_paid_amount")
        private Double TotalPaidAmount;

        @JsonProperty("overpaid_amount")
        private Double OverpaidAmount;

        @JsonProperty("installment_amount")
        private Double InstallmentAmount;
    }

    @Data
    public static class FeeDetail {
        @JsonProperty("type")
        private String Type;

        @JsonProperty("amount")
        private Double Amount;

        @JsonProperty("fee_payer")
        private String FeePayer;
    }

    @Data
    public static class Card {
        @JsonProperty("first_six_digits")
        private String FirstSixDigits;

        @JsonProperty("last_four_digits")
        private String LastFourDigits;

        @JsonProperty("expiration_month")
        private Integer ExpirationMonth;

        @JsonProperty("expiration_year")
        private Integer ExpirationYear;

        @JsonProperty("date_created")
        private LocalDateTime DateCreated;

        @JsonProperty("date_last_updated")
        private LocalDateTime DateLastUpdated;

        @JsonProperty("cardholder")
        private CardHolder Cardholder;
    }

    @Data
    public static class CardHolder {
        @JsonProperty("name")
        private String Name;

        @JsonProperty("identification")
        private Identification Identification;
    }

    @Data
    public static class PointOfInteraction {
        @JsonProperty("type")
        private String Type;

        @JsonProperty("application_data")
        private ApplicationData ApplicationData;

        @JsonProperty("transaction_data")
        private TransactionData TransactionData;
    }

    @Data
    public static class ApplicationData {
        @JsonProperty("name")
        private String Name;

        @JsonProperty("version")
        private String Version;
    }

    @Data
    public static class TransactionData {
        @JsonProperty("qr_code_base64")
        private String QrCodeBase64;

        @JsonProperty("qr_code")
        private String QrCode;

        @JsonProperty("ticket_url")
        private String TicketUrl;
    }
}
