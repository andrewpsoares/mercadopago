package faculdade.mercadopago.adapter.driven.entity;

import faculdade.mercadopago.core.domain.dto.AlterarPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity(name = "Pedido")
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"CODIGO"})
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CODIGO;
    private Long USUARIOCODIGO;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum STATUS;
    private Double VALORTOTAL;
    private Date DATAHORASOLICITACAO;
    private Time TEMPOTOTALPREPARO;

    @OneToMany(mappedBy = "pedidos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItemEntity> Itens;

    public void alterarStatusPedido( AlterarPedidoDto dados) {
        if (dados.status() != null){
            this.STATUS = dados.status();
        }
    }
}
