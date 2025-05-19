package faculdade.mercadopago.core.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotNull(message = "Identificar Usuário é obrigatório")
    private Boolean identificar_usuario;
    private String nome;
    private String cpf;
    private String email;


}
