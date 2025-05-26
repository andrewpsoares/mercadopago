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
@Entity
@EqualsAndHashCode(of = "Codigo")
public class PedidoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Codigo;

    @ManyToOne
    @JoinColumn(name = "pedidocodigo")
    @JsonIgnore
    private PedidoEntity pedido;

    @ManyToOne
    @JoinColumn(name = "produtocodigo")
    @JsonIgnore
    private ProdutoEntity produtocodigo;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "precounitario")
    private BigDecimal precounitario;

    @Column(name = "precototal")
    private BigDecimal precototal;

    public BigDecimal calcularPrecoTotalItem() {
        if (this.produtocodigo != null && this.produtocodigo.getPreco() != null && quantidade > 0) {
           return this.precototal = this.produtocodigo.getPreco().multiply(BigDecimal.valueOf(quantidade));
        } else {
            return this.precototal = BigDecimal.ZERO;
        }
    }
}
