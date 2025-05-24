package faculdade.mercadopago.core.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;

@Data
public class NewProdutoDto {
    private String nome;
    private String descricao;
    private long categoria;
    private BigDecimal preco;
    private Time tempopreparo;
}
