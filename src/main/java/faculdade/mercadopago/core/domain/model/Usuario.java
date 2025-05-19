package faculdade.mercadopago.core.domain.model;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String nome;
    private String cpf;
    private String email;

}
