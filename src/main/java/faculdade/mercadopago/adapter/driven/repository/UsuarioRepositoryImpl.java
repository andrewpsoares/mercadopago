package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.core.domain.model.Usuario;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public abstract class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioRepository usuarioRepository;

    public UsuarioRepositoryImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .map(entity -> new Usuario(entity.getNome(), entity.getCpf(), entity.getEmail()));
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(usuario.getNome());
        entity.setCpf(usuario.getCpf());
        entity.setEmail(usuario.getEmail());
        UsuarioEntity savedEntity = usuarioRepository.save(entity);
        return new Usuario(savedEntity.getNome(), savedEntity.getCpf(), savedEntity.getEmail());
    }
}
