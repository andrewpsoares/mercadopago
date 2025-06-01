package faculdade.mercadopago.adapter.driven.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "entregas")
@Getter
@Setter
@EqualsAndHashCode(of = "Codigo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntregaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Codigo;

    @OneToOne
    @JoinColumn(name = "pedidocodigo")
    private PedidoEntity PedidoCodigo;

    @Column(name = "datahoraentrega")
    private LocalDateTime DataHoraEntrega;
}
