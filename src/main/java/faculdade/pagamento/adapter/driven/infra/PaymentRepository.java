package faculdade.pagamento.adapter.driven.infra;

import faculdade.pagamento.core.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
