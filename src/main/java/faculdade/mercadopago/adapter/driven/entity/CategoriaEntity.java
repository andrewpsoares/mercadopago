package faculdade.mercadopago.adapter.driven.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private long codigo;

    @Column(name = "nome")
    private String nome;
}
