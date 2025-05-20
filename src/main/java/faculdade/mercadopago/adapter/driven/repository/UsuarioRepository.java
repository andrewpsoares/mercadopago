package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.core.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {

    Optional<Usuario> findByCpf(String cpf);
    Usuario save(Usuario usuario);
}
