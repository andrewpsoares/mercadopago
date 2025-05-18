package faculdade.pagamento.adapter.driven.infra;

import faculdade.pagamento.core.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity,Long> {

    Optional<Usuario> findByCpf(String cpf);
}
