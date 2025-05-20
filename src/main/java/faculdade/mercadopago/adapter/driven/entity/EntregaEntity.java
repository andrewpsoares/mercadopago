package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Table(name = "entregas")
@Data
public class EntregaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Codigo;

    @Column(name = "pedidocodigo")
    private long PedidoCodigo;

    @Column(name = "datahoraentrega")
    private LocalDateTime DataHoraEntrega;
}
