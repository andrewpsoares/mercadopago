package faculdade.mercadopago.core.domain.port;

import faculdade.mercadopago.core.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByCpf(String cpf);
    Usuario save(Usuario usuario);
}
