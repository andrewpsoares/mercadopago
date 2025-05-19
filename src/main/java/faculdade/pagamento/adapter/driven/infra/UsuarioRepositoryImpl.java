package faculdade.pagamento.adapter.driven.infra;

import faculdade.pagamento.core.domain.model.Usuario;
import faculdade.pagamento.core.domain.port.UsuarioRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryImpl(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioJpaRepository.findByCpf(cpf)
                .map(entity -> new Usuario(entity.getNome(), entity.getCpf(), entity.getEmail()));
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(usuario.getNome());
        entity.setCpf(usuario.getCpf());
        entity.setEmail(usuario.getEmail());
        UsuarioEntity savedEntity = usuarioJpaRepository.save(entity);
        return new Usuario(savedEntity.getNome(), savedEntity.getCpf(), savedEntity.getEmail());
    }
}
