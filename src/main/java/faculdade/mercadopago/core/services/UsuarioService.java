package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.model.Usuario;
import faculdade.mercadopago.core.domain.model.UsuarioRequest;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Builder
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public ApiResponse<Usuario> buscarUsuarioCpf(String cpf) throws Exception {
        UsuarioEntity usuarioEntity = usuarioRepository.findByCpf(cpf);

        var usuario = Usuario.builder()
                    .codigo(usuarioEntity.getCodigo())
                    .nome(usuarioEntity.getNome())
                    .cpf(usuarioEntity.getCpf())
                    .email(usuarioEntity.getEmail())
                    .build();

        var apiResponse = new ApiResponse<Usuario>();
        apiResponse.setSuccess(true);
        apiResponse.setData(usuario);
        return apiResponse;
    }


    public ApiResponse<Usuario> processarUsuario(UsuarioRequest request) throws Exception {
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

        var usuarioEntity = usuarioRepository.save(usuario);

        var response = Usuario.builder()
                    .codigo(usuarioEntity.getCodigo())
                     .nome(usuarioEntity.getNome())
                    .cpf(usuarioEntity.getCpf())
                    .email(usuarioEntity.getEmail())
                    .build();

        var apiResponse = new ApiResponse<Usuario>();
        apiResponse.setSuccess(true);
        apiResponse.setData(response);
        return apiResponse;
    }


}
