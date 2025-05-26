package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;

public record ViewPedidoDto(
        Long codigo,
        Long usuariocodigo,
        StatusPedidoEnum status,
        BigDecimal valortotal,
        LocalDateTime datahorasolicitacao,
        Time tempototalpreparo
) {
    public ViewPedidoDto(PedidoEntity dados){

        this(
                dados.getCodigo(),
                dados.getUsuario().getCodigo(),
                dados.getStatus(),
                dados.getValortotal(),
                dados.getDatahorasolicitacao(),
                new Time(dados.getTempototalpreparo().getTime())
        );
    }
}