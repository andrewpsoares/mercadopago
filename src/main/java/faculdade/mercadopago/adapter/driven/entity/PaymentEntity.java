package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pagamentos")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO")
    private Long Id;

    @Column(name = "PEDIDOCODIGO")
    private Long OrderId;

    @Column(name = "VALORPAGO")
    private BigDecimal Value;

    @Column(name = "STATUS")
    private String Status;

    @Column(name = "DATAHORAPAGAMENTO")
    private LocalDateTime PaymentDateTime;
}
