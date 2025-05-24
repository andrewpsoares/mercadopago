package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public record ListarPedidoDto(
        Long codigo,
        Long usuariocodigo,
        StatusPedidoEnum status,
        BigDecimal valortotal,
        Date datahorasolicitacao,
        Time tempototalpreparo
) {
    public ListarPedidoDto(PedidoEntity dados){

        this(
                dados.getCodigo(),
                dados.getUsuariocodigo(),
                dados.getStatus(),
                dados.getValortotal(),
                dados.getDatahorasolicitacao(),
                new Time(dados.getTempototalpreparo().getTime())
        );
    }
}