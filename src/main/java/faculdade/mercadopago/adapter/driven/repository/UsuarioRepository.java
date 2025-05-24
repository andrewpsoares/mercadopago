package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.core.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {

    UsuarioEntity findByCpf(String cpf);
    UsuarioEntity save(Usuario usuario);
    UsuarioEntity findByCodigo(Long codigo);
}
