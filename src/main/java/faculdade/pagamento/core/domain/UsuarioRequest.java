package faculdade.pagamento.core.domain;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class UsuarioRequest {

    @NotNull(message = "Identificar Usuário é obrigatório")
    private Boolean identificar_usuario;
    private String nome;
    private String cpf;
    private String email;


}
