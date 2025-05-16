package faculdade.pagamento.adapter.driver;

import faculdade.pagamento.core.domain.dto.NewPaymentDto;
import faculdade.pagamento.core.domain.QrOrderResponse;
import faculdade.pagamento.core.domain.Payment;
import faculdade.pagamento.core.domain.QrPaymentResponse;
import faculdade.pagamento.core.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public Payment GenerateQrCode(@RequestBody NewPaymentDto paymentDto) {
        return service.GenerateQrCode(paymentDto);
    }

    @PostMapping(path = "/mercadopago")
    public Payment ReponseQrCode(@RequestBody QrPaymentResponse paymentResponseDto) {
        var teste = new Payment();
        return teste;
    }

    @GetMapping
    public void GetStatus(@RequestBody QrOrderResponse responseGenerateQrCodeDto) {
        var teste = responseGenerateQrCodeDto;
    }
}
