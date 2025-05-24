package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "filapedidospreparacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "filapedidospreparacao")
public class FilaPedidosPreparacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @OneToOne
    @JoinColumn(name = "pedidocodigo")
    private PedidoEntity pedidocodigo;
}
