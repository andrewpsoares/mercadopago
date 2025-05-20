package faculdade.mercadopago.core.services;

import faculdade.mercadopago.core.domain.model.UsuarioRequest;
import faculdade.mercadopago.core.domain.model.Usuario;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Builder
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> buscarUsuarioCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Usuario processarUsuario(UsuarioRequest request) {
        if (request.getIdentificar_usuario()) {

            Usuario usuario = Usuario.builder()
                            .nome(request.getNome())
                            .cpf(request.getCpf())
                            .email(request.getEmail())
                            .build();

            return usuarioRepository.save(usuario);

        } else {

            Usuario usuarioPadrao = Usuario.builder()
                    .nome("USUARIO PADRAO")
                    .cpf("00000000000")
                    .email("padrao@email.com")
                    .build();

            return usuarioRepository.save(usuarioPadrao);
        }
    }
}
