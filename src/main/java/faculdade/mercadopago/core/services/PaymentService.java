package faculdade.mercadopago.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faculdade.mercadopago.AppConstants;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.applications.ports.BadRequestException;
import faculdade.mercadopago.core.domain.*;
import faculdade.mercadopago.core.domain.dto.NewPaymentDto;
import faculdade.mercadopago.adapter.driven.infra.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {
    private final PaymentRepository _paymentRepository;
    private final MercadoPagoService _mercadoPagoService;

    public PaymentService(PaymentRepository paymentRepository, MercadoPagoService mercadoPagoService) {
        _paymentRepository = paymentRepository;
        _mercadoPagoService = mercadoPagoService;
    }

    public ApiResponse<QrOrderResponse> GenerateQrCode(NewPaymentDto paymentDto) {
        var item = new QrOrderRequest.Item (
                "123",
                "lanche",
                "titulo",
                "lanche do bom",
                1.0,
                "unit",
                6.0,
                6.0
        );
        var list = new ArrayList<QrOrderRequest.Item>(List.of(item));

        var request = new QrOrderRequest(
                String.valueOf(paymentDto.OrderId),
                "Lanchonete",
                "Descricao",
                paymentDto.TotalAmount,
                AppConstants.NOTIFICATION_URL,
                list
        );

        var url = AppConstants.BASEURL_MERCADOPAGO + AppConstants.GENERATEQRCODEURL_MERCADOPAGO;
        var response = _mercadoPagoService.SendRequest(url, request, QrOrderResponse.class, null);
        var apiResponse = new ApiResponse<QrOrderResponse>();

        if (response.getStatusCode().is2xxSuccessful()) {
            apiResponse.setSuccess(true);
            apiResponse.setData((QrOrderResponse) response.getBody());
        } else {
            apiResponse.setSuccess(false);
            try {
                var mapper = new ObjectMapper();
                var errorResponse = mapper.readValue((String) response.getBody(), ApiResponse.Err.class);
                apiResponse.addError("Erro ao gerar QR Code: " + errorResponse.getError(), errorResponse.getMessage());
            } catch (Exception ex) {
                apiResponse.addError("Erro ao interpretar resposta de erro: ",(String) response.getBody());
            }
        }

        return apiResponse;
    }

    public ApiResponse ConfirmPayment(NewPaymentDto newPaymentDto) {
        var request = new PixPaymentRequest();
        request.setTransactionAmount(newPaymentDto.TotalAmount);
        request.setPaymentMethodId("pix");
        request.setDescription("Compra de teste QR");
        request.setExternalReference(String.valueOf(newPaymentDto.OrderId));
        request.setInstallments(1);

        PixPaymentRequest.Identification identification = new PixPaymentRequest.Identification("11111111111", "CPF");
        PixPaymentRequest.Payer payer = new PixPaymentRequest.Payer("Teste", "Test", "test_user_249718537@testuser.com", identification);

        request.setPayer(payer);

        var url = AppConstants.BASEURL_MERCADOPAGO + AppConstants.CONFIRMPAYMENT_MERCADOPAGO;
        var extraHeaders = Map.of("X-Idempotency-Key", String.format("order-%s-y423g4ygyGSa56d7sb", String.valueOf(newPaymentDto.OrderId)));
        var response = _mercadoPagoService.SendRequest(url, request, QrOrderResponse.class, extraHeaders);
        var apiResponse = new ApiResponse<QrOrderResponse>();

        if (response.getStatusCode().is2xxSuccessful()) {
            apiResponse.setSuccess(true);
            apiResponse.setData((QrOrderResponse) response.getBody());
            CreatePayment(101, 6, ""); // talvez seja melhor salvar o pagamento primeiro e alterar o status apos a confimação do mercado pago
        } else {
            apiResponse.setSuccess(false);
            try {
                var mapper = new ObjectMapper();
                var errorResponse = mapper.readValue((String) response.getBody(), ApiResponse.Err.class);
                apiResponse.addError("Erro ao gerar QR Code: " + errorResponse.getError(), errorResponse.getMessage());
            } catch (Exception ex) {
                apiResponse.addError("Erro ao interpretar resposta de erro: ",(String) response.getBody());
            }
        }

        return apiResponse;
    }

    public boolean CreatePayment(long orderId, double value, String status) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setValue(value);
        payment.setStatus(status);
        payment.setPaymentDateTime(LocalDateTime.now());

        try {
            var obj = _paymentRepository.save(payment);
            return obj.getId() != null;
        } catch (Exception e) {
            ApiResponse<Payment> response = new ApiResponse<>();
            response.setSuccess(false);
            response.addError("Erro ao salvar pagamento", e.getMessage());
            return false;
        }
    }
}
