package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;

@Data
@Entity
@Table(name = "produtos")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;

    private String nome;
    private String descricao;

    @Column(name = "categoriacodigo")
    private long categoria;

    private BigDecimal preco;
    private Time tempopreparo;
}
