package faculdade.mercadopago.adapter.driven.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import faculdade.mercadopago.core.domain.dto.AlterarPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"codigo"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "usuariocodigo")
    private Long usuariocodigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedidoEnum status;

    @Column(name = "valortotal")
    private BigDecimal valortotal;

    @Column(name = "datahorasolicitacao")
    private Date datahorasolicitacao;

    @Column(name = "tempototalpreparo")
    private Time tempototalpreparo;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItemEntity> itens;

    public void alterarStatusPedido( AlterarPedidoDto dados) {
        if (dados.status() != null){
            this.status = dados.status();
        }
    }
}
