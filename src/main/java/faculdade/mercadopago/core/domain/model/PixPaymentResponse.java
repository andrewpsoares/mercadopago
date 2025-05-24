package faculdade.mercadopago.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PixPaymentResponse {
    private Object accounts_info;
    private List<Object> acquirer_reconciliation;

    @JsonProperty("additional_info")
    private AdditionalInfo additionalInfo;

    private String authorization_code;
    private boolean binary_mode;
    private String brand_id;
    private String build_version;
    private String call_for_authorize_id;
    private String callback_url;
    private boolean captured;
    private Card card;

    @JsonProperty("charges_details")
    private List<ChargesDetail> chargesDetails;

    private Long collector_id;
    private String corporation_id;
    private String counter_currency;
    private double coupon_amount;
    private String currency_id;
    private String date_approved;
    private String date_created;
    private String date_last_updated;
    private String date_of_expiration;
    private String deduction_schema;
    private String description;
    private String differential_pricing_id;
    private String external_reference;
    private List<Object> fee_details;
    private String financing_group;
    private Long id;
    private int installments;
    private String integrator_id;
    private String issuer_id;
    private boolean live_mode;
    private String marketplace_owner;
    private String merchant_account_id;
    private String merchant_number;
    private Map<String, Object> metadata;
    private String money_release_date;
    private String money_release_schema;
    private String money_release_status;
    private String notification_url;
    private String operation_type;
    private Order order;
    private Payer payer;

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;

    private String payment_method_id;
    private String payment_type_id;
    private String platform_id;

    @JsonProperty("point_of_interaction")
    private PointOfInteraction pointOfInteraction;

    private String pos_id;
    private String processing_mode;
    private List<Object> refunds;
    private String release_info;
    private double shipping_amount;
    private String sponsor_id;
    private String statement_descriptor;
    private String status;
    private String status_detail;
    private String store_id;
    private String tags;
    private double taxes_amount;
    private double transaction_amount;
    private double transaction_amount_refunded;

    @JsonProperty("transaction_details")
    private TransactionDetails transactionDetails;

    // Subclasses

    @Data
    public static class AdditionalInfo {
        private String tracking_id;
    }

    @Data
    public static class Card {}

    @Data
    public static class ChargesDetail {
        private Accounts accounts;
        private Amounts amounts;
        private long client_id;
        private String date_created;
        private String id;
        private String last_updated;
        private Metadata metadata;
        private String name;

        @JsonProperty("refund_charges")
        private List<Object> refundCharges;

        private String reserve_id;
        private String type;

        @Data
        public static class Accounts {
            private String from;
            private String to;
        }

        @Data
        public static class Amounts {
            private double original;
            private double refunded;
        }

        @Data
        public static class Metadata {
            private String reason;
            private String source;
        }
    }

    @Data
    public static class Order {}

    @Data
    public static class Payer {
        private Identification identification;
        private String entity_type;
        private Phone phone;
        private String last_name;
        private String id;
        private String type;
        private String first_name;
        private String email;

        @Data
        public static class Identification {
            private String number;
            private String type;
        }

        @Data
        public static class Phone {
            private String number;
            private String extension;
            private String area_code;
        }
    }

    @Data
    public static class PaymentMethod {
        private String id;
        private String issuer_id;
        private String type;
    }

    @Data
    public static class PointOfInteraction {
        @JsonProperty("application_data")
        private ApplicationData applicationData;

        @JsonProperty("business_info")
        private BusinessInfo businessInfo;

        private Location location;

        @JsonProperty("transaction_data")
        private TransactionData transactionData;

        private String type;

        @Data
        public static class ApplicationData {
            private String name;
            private String operating_system;
            private String version;
        }

        @Data
        public static class BusinessInfo {
            private String branch;
            private String sub_unit;
            private String unit;
        }

        @Data
        public static class Location {
            private String source;
            private String state_id;
        }

        @Data
        public static class TransactionData {
            @JsonProperty("bank_info")
            private BankInfo bankInfo;

            private String bank_transfer_id;
            private String e2e_id;
            private String financial_institution;

            @JsonProperty("infringement_notification")
            private InfringementNotification infringementNotification;

            private String qr_code;
            private String qr_code_base64;
            private String ticket_url;
            private String transaction_id;

            @Data
            public static class BankInfo {
                private Account collector;
                private Boolean is_same_bank_account_owner;
                private String origin_bank_id;
                private String origin_wallet_id;
                private Account payer;

                @Data
                public static class Account {
                    private String account_holder_name;
                    private String account_id;
                    private String long_name;
                    private String transfer_account_id;
                    private String branch;
                    private String external_account_id;
                    private String id;
                    private Identification identification;
                    private Boolean is_end_consumer;

                    @Data
                    public static class Identification {
                        private String number;
                        private String type;
                    }
                }
            }

            @Data
            public static class InfringementNotification {
                private String status;
                private String type;
            }
        }
    }

    @Data
    public static class TransactionDetails {
        private String acquirer_reference;
        private String bank_transfer_id;
        private String external_resource_url;
        private String financial_institution;
        private double installment_amount;
        private double net_received_amount;
        private double overpaid_amount;
        private String payable_deferral_period;
        private String payment_method_reference_id;
        private double total_paid_amount;
        private String transaction_id;
    }
}
