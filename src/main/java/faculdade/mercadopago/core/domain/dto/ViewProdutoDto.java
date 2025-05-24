package faculdade.mercadopago.core.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;

@Data
@Builder
public class ViewProdutoDto {
    private long codigo;
    private String nome;
    private String descricao;
    private long categoria;
    private BigDecimal preco;
    private Time tempopreparo;
}
