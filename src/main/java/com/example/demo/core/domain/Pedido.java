package com.example.demo.core.domain;

import com.example.demo.core.domain.dto.AlterarPedidoDto;
import com.example.demo.core.domain.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Entity(name = "Pedido")
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"CODIGO"})
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CODIGO;
    private Long USUARIOCODIGO;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum STATUS;
    private Double VALORTOTAL;
    private Date DATAHORASOLICITACAO;
    private Time TEMPOTOTALPREPARO;

    public void alterarStatusPedido( AlterarPedidoDto dados) {
        if (dados.status() != null){
            this.STATUS = dados.status();
        }
    }
}
