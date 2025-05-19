package faculdade.mercadopago.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "pagamentos")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long OrderId;
    private Double Value;
    private String Status;
    private LocalDateTime PaymentDateTime;
}
