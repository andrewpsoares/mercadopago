package faculdade.pagamento.core.services;

import faculdade.pagamento.core.domain.dto.NewPaymentDto;
import faculdade.pagamento.core.domain.model.Payment;
import faculdade.pagamento.adapter.driven.infra.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository _repository;
    private final MercadoPagoService _serviceMercadoPago;

    public PaymentService(PaymentRepository repository, MercadoPagoService serviceMercadoPago) {
        _repository = repository;
        _serviceMercadoPago = serviceMercadoPago;
    }

    public Payment GenerateQrCode(NewPaymentDto paymentDto) {
        var teste = _serviceMercadoPago.GenerateQrCode(paymentDto);
        return new Payment();
//        return repository.save(produto);
    }
}
