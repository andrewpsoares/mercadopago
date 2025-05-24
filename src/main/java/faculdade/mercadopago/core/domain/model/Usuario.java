package faculdade.mercadopago.core.domain.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private long codigo;
    private String nome;
    private String cpf;
    private String email;

}
