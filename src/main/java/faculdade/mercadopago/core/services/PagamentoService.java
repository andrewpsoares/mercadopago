package faculdade.mercadopago.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import faculdade.mercadopago.AppConstants;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewPaymentDto;
import faculdade.mercadopago.adapter.driven.repository.PaymentRepository;
import faculdade.mercadopago.adapter.driven.entity.PagamentoEntity;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.domain.model.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PagamentoService {
    private final PaymentRepository _paymentRepository;
    private final MercadoPagoService _mercadoPagoService;
    private final PedidoRepository _pedidoRepository;

    public PagamentoService(PaymentRepository paymentRepository, MercadoPagoService mercadoPagoService,
                            PedidoRepository pedidoRepository) {
        _paymentRepository = paymentRepository;
        _mercadoPagoService = mercadoPagoService;
        _pedidoRepository = pedidoRepository;
    }

    public ApiResponse<QrOrderResponse> GerarQrCode(NewPaymentDto paymentDto) {
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
        var response = _mercadoPagoService.SendRequest(url, HttpMethod.POST, request, QrOrderResponse.class, null);
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

//    public ApiResponse ConfirmPayment(NewPaymentDto newPaymentDto) {
//        var request = new PixPaymentRequest();
//        request.setTransactionAmount(newPaymentDto.TotalAmount);
//        request.setPaymentMethodId("pix");
//        request.setDescription("Compra de teste QR");
//        request.setExternalReference(String.valueOf(newPaymentDto.OrderId));
//        request.setInstallments(1);
//
//        PixPaymentRequest.Identification identification = new PixPaymentRequest.Identification("11111111111", "CPF");
//        PixPaymentRequest.Payer payer = new PixPaymentRequest.Payer("Teste", "Test", "test_user_249718537@testuser.com", identification);
//
//        request.setPayer(payer);
//
//        var url = AppConstants.BASEURL_MERCADOPAGO + AppConstants.CONFIRMPAYMENT_MERCADOPAGO;
//        var extraHeaders = Map.of("X-Idempotency-Key", String.format("order-%s-y423g4ygyGSa56d7sb", String.valueOf(newPaymentDto.OrderId)));
//        var response = _mercadoPagoService.SendRequest(url, HttpMethod.POST, request, QrOrderResponse.class, extraHeaders);
//        var apiResponse = new ApiResponse<QrOrderResponse>();
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            apiResponse.setSuccess(true);
//            apiResponse.setData((QrOrderResponse) response.getBody());
//            CreatePagamento(101, BigDecimal.valueOf(6), ""); // talvez seja melhor salvar o pagamento primeiro e alterar o status apos a confimação do mercado pago
//        } else {
//            apiResponse.setSuccess(false);
//            try {
//                var mapper = new ObjectMapper();
//                var errorResponse = mapper.readValue((String) response.getBody(), ApiResponse.Err.class);
//                apiResponse.addError("Erro ao gerar QR Code: " + errorResponse.getError(), errorResponse.getMessage());
//            } catch (Exception ex) {
//                apiResponse.addError("Erro ao interpretar resposta de erro: ",(String) response.getBody());
//            }
//        }
//
//        return apiResponse;
//    }

    public ApiResponse ConfirmaPagamento (QrOrderPagamentoResponse orderResponse) {
        var url = AppConstants.BASEURL_MERCADOPAGO + AppConstants.CONFIRMPAYMENT_MERCADOPAGO + "/" + orderResponse.getId();
        var response = _mercadoPagoService.SendRequest(url, HttpMethod.GET, PixPaymentResponse.class);
        var apiResponse = new ApiResponse<>();

        if (response.getStatusCode().is2xxSuccessful()) {
            apiResponse.setSuccess(true);
            apiResponse.setData((PixPaymentResponse) response.getBody());

            PixPaymentResponse body = (PixPaymentResponse) response.getBody();
            String codigo = body.getExternal_reference();
            String status = body.getStatus();
            Double valorPago = 0.0;
            if (body.getTransactionDetails() != null) {
                valorPago = body.getTransactionDetails().getTotal_paid_amount();
            }

            if (status.equals("approved")) {
                var resultado = CreatePagamento(Long.parseLong(codigo), BigDecimal.valueOf(valorPago), status);
                if (resultado) {
                    apiResponse.setData(null);
                }
            } else {
                apiResponse.setSuccess(false);
                apiResponse.addError("Pagamento","Pagamento não foi aprovado.");
            }
        } else {
            apiResponse.setSuccess(false);
            try {
                var mapper = new ObjectMapper();
                var errorResponse = mapper.readValue((String) response.getBody(), ApiResponse.Err.class);
                apiResponse.addError("Erro na confirmação de pagamento: " + errorResponse.getError(), errorResponse.getMessage());
            } catch (Exception ex) {
                apiResponse.addError("Erro ao interpretar resposta: ",(String) response.getBody());
            }
        }
        return apiResponse;
    }

    public boolean CreatePagamento(long orderId, BigDecimal value, String status) {
        var pedidoOpcional = _pedidoRepository.findById(orderId);

        if (pedidoOpcional.isEmpty()) {
            return false;
        }

        var pedido = pedidoOpcional.get();
        PagamentoEntity pagamento = new PagamentoEntity(
                pedido,
                value,
                status
        );

        try {
            var obj = _paymentRepository.save(pagamento);
            return obj.getId() != null;
        } catch (Exception e) {
            ApiResponse<PagamentoEntity> response = new ApiResponse<>();
            response.setSuccess(false);
            response.addError("Erro ao salvar pagamento", e.getMessage());
            return false;
        }
    }
}
