package faculdade.mercadopago.core.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewCategoriaDto {
    private long codigo;
    private String nome;
}
