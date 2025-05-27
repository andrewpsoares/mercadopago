package faculdade.mercadopago.adapter.driven.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

@Table(name = "pedidoitem")
@Data
@Builder
@Entity
@EqualsAndHashCode(of = "Codigo")
@NoArgsConstructor
@AllArgsConstructor
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

    public Time calcularTempoTotalItem() {
        LocalTime tempoUnitario = produtocodigo.getTempopreparo().toLocalTime();
        Duration duracaoUnitario = Duration.ofHours(tempoUnitario.getHour())
                .plusMinutes(tempoUnitario.getMinute())
                .plusSeconds(tempoUnitario.getSecond());

        Duration duracaoTotal = duracaoUnitario.multipliedBy(quantidade);
        LocalTime tempoTotal = LocalTime.MIDNIGHT.plus(duracaoTotal);

        return Time.valueOf(tempoTotal);
    }
}
