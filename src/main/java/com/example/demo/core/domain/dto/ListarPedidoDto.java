package com.example.demo.core.domain.dto;

import com.example.demo.core.domain.enums.StatusPedidoEnum;
import com.example.demo.core.domain.Pedido;

import java.sql.Time;
import java.util.Date;

public record ListarPedidoDto(
         Long CODIGO,
         Long USUARIOCODIGO,
         StatusPedidoEnum STATUS,
         Double VALORTOTAL,
         Date DATAHORASOLICITACAO,
         Time TEMPOTOTALPREPARO
) {
    public ListarPedidoDto(Pedido dados){
        this(
                dados.getCODIGO(),
                dados.getUSUARIOCODIGO(),
                dados.getSTATUS(),
                dados.getVALORTOTAL(),
                dados.getDATAHORASOLICITACAO(),
                new Time(dados.getTEMPOTOTALPREPARO().getTime())
                );
    }
}
