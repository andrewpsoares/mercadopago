package faculdade.mercadopago.adapter.driven.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import faculdade.mercadopago.core.domain.dto.AlterarPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"codigo"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "usuariocodigo")
    private UsuarioEntity usuario;

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

    public void alterarStatusPedido(StatusPedidoEnum status) {
        if (status != null){
            this.status = status;
        }
    }


    public void setUsuario(@NotNull Long usuariocodigo) {
    }

    public Long getUsuariocodigo() {
        return this.usuario.getCodigo();
    }
}
