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
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        if (request.getIdentificar_usuario()) {
            usuarioEntity.setNome(request.getNome());
            usuarioEntity.setCpf(request.getCpf());
            usuarioEntity.setEmail(request.getEmail());
        } else {
            usuarioEntity.setNome("USUARIO PADRAO");
            usuarioEntity.setCpf("00000000000");
            usuarioEntity.setEmail("padrao@email.com");
        }

        UsuarioEntity salvo = usuarioRepository.save(usuarioEntity);

        return Usuario.builder()
                .codigo(salvo.getCodigo())
                .nome(salvo.getNome())
                .cpf(salvo.getCpf())
                .email(salvo.getEmail())
                .build();
    }


}
