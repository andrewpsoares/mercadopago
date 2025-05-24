package faculdade.mercadopago.adapter.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewPaymentDto;
import faculdade.mercadopago.core.domain.model.QrOrderPagamentoResponse;
import faculdade.mercadopago.core.domain.model.QrOrderResponse;
import faculdade.mercadopago.core.services.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PagamentoController {
    private final PagamentoService _pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        _pagamentoService = pagamentoService;
    }

    @Operation(summary = "Gerar um Qr Code", description = "Retorna uma string do Qr Code")
    @PostMapping
    public ResponseEntity<ApiResponse<QrOrderResponse>> GenerateQrCode(@RequestBody NewPaymentDto paymentDto) throws JsonProcessingException {
        ApiResponse<QrOrderResponse> response = _pagamentoService.GerarQrCode(paymentDto);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Confirmar pagamento Mercado Pago", description = "Recebe a confirmação de pagamento recebido pelo mercado pago")
    @PostMapping(path = "/mercadopago/confirmapagamento")
    public ResponseEntity<ApiResponse> ConfirmaPagamento(@RequestBody QrOrderPagamentoResponse pagamentoResponse) {
        var response = _pagamentoService.ConfirmaPagamento(pagamentoResponse);
        if (response.isSuccess()) return ResponseEntity.ok(response);
        else return ResponseEntity.badRequest().body(response);
    }

}
