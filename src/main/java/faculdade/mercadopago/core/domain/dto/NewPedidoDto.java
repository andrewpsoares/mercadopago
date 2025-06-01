package faculdade.mercadopago.core.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewPedidoDto {

    private long usuario;
    private List<NewPedidoItemDto>itens;


}



