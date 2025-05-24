package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import faculdade.mercadopago.core.applications.ports.BadRequestException;
import faculdade.mercadopago.core.domain.model.Usuario;
import faculdade.mercadopago.core.domain.model.UsuarioRequest;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Builder
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarUsuarioCpf(String cpf) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByCpf(cpf);

        if (usuarioEntity == null) {
            throw new BadRequestException.UsuarioNaoEncontradoException("Usuário não encontrado com CPF: " + cpf);
        }

        return Usuario.builder()
                .codigo(usuarioEntity.getCodigo())
                .nome(usuarioEntity.getNome())
                .cpf(usuarioEntity.getCpf())
                .email(usuarioEntity.getEmail())
                .build();
    }


    public Usuario processarUsuario(UsuarioRequest request) {
        UsuarioEntity usuario = new UsuarioEntity();

        if (request.getIdentificar_usuario()) {
            usuario.setNome(request.getNome());
            usuario.setCpf(request.getCpf());
            usuario.setEmail(request.getEmail());
        } else {
            usuario.setNome("USUARIO PADRAO");
            usuario.setCpf("00000000000");
            usuario.setEmail("padrao@email.com");
        }

        UsuarioEntity usuarioEntity = usuarioRepository.save(usuario);

        return Usuario.builder()
                .codigo(usuarioEntity.getCodigo())
                .nome(usuarioEntity.getNome())
                .cpf(usuarioEntity.getCpf())
                .email(usuarioEntity.getEmail())
                .build();
    }


}
