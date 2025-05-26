package faculdade.mercadopago.adapter.driven.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
@Getter
@Setter
@EqualsAndHashCode(of = "codigo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntregaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @OneToOne
    @JoinColumn(name = "pedidocodigo")
    private PedidoEntity pedidocodigo;

    @Column(name = "datahoraentrega")
    private LocalDateTime datahoraentrega;

    @PrePersist
    public void prePersist() {
        if (this.datahoraentrega == null) {
            this.datahoraentrega = LocalDateTime.now();
        }
    }
}
