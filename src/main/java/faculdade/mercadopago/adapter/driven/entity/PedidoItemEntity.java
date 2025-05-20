package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Table(name = "pedidoitem")
@Data
public class PedidoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Codigo;

    @ManyToOne
    @JoinColumn(name = "pedidocodigo")
    @Column(name = "pedidocodigo")
    private long PedidoCodigo;

    @Column(name = "produtocodigo")
    private long ProdutoCodigo;

    private double Quantidade;

    @Column(name = "precounitario")
    private BigDecimal PrecoUnitario;

    @Column(name = "precototal")
    private BigDecimal PrecoTotal;
}
