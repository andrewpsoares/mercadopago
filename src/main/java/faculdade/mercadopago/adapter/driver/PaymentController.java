package faculdade.mercadopago.adapter.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewPaymentDto;
import faculdade.mercadopago.core.domain.model.QrOrderResponse;
import faculdade.mercadopago.core.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService _paymentService;

    public PaymentController(PaymentService paymentService) {
        _paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QrOrderResponse>> GenerateQrCode(@RequestBody NewPaymentDto paymentDto) throws JsonProcessingException {
        ApiResponse<QrOrderResponse> response = _paymentService.GenerateQrCode(paymentDto);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

//    @PostMapping(path = "/mercadopago")
//    public Payment ReponseQrCode(@RequestBody QrPaymentResponse paymentResponseDto) {
//        var teste = new Payment();
//        return teste;
//    }
//
//    @GetMapping
//    public void GetStatus(@RequestBody QrOrderResponse responseGenerateQrCodeDto) {
//        var teste = responseGenerateQrCodeDto;
//    }
}
