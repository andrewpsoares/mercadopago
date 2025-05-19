package faculdade.pagamento.core.domain.port;

import faculdade.pagamento.core.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByCpf(String cpf);
    Usuario save(Usuario usuario);
}
