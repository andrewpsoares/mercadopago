package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "pagamentos")
public class PagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO")
    private Long Id;

    @NonNull
    @OneToOne
    @JoinColumn(name = "pedidocodigo", referencedColumnName = "CODIGO")
    private PedidoEntity PedidoId;

    @NonNull
    @Column(name = "VALORPAGO")
    private BigDecimal Valor;

    @NonNull
    @Column(name = "STATUS")
    private String Status;

    @Column(name = "DATAHORAPAGAMENTO")
    private LocalDateTime DataPagamento;

    @PrePersist
    public void prePersist() {
        if (this.DataPagamento == null) {
            this.DataPagamento = LocalDateTime.now();
        }
    }
}
