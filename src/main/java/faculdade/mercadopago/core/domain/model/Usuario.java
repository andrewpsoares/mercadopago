package faculdade.pagamento.core.domain.model;

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
