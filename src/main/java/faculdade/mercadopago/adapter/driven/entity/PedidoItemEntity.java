package faculdade.mercadopago.adapter.driven.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "pedidoitem")
@Getter
@Setter
@Entity(name = "pedidoitem")
@EqualsAndHashCode(of = "Codigo")
public class PedidoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Codigo;

    @ManyToOne
    @JoinColumn(name = "pedidocodigo")
    @JsonIgnore
    private PedidoEntity pedido;

    @Column(name = "produtocodigo")
    private long ProdutoCodigo;

    @Column(name = "quantidade")
    private int Quantidade;

    @Column(name = "precounitario")
    private BigDecimal PrecoUnitario;

    @Column(name = "precototal")
    private BigDecimal PrecoTotal;
}
