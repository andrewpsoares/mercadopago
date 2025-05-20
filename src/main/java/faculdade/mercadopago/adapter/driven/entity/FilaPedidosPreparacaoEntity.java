package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "filapedidospreparacao")
@Data
public class FilaPedidosPreparacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Codigo;

    @Column(name = "pedidocodigo")
    private long PedidoCodigo;
}
