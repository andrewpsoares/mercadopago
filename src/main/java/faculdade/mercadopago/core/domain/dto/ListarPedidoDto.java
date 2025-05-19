package faculdade.mercadopago.core.domain.dto;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.domain.model.Pedido;

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