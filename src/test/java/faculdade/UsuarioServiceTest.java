package faculdade;

import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.model.Usuario;
import faculdade.mercadopago.core.domain.model.UsuarioRequest;
import faculdade.mercadopago.core.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarUsuarioCpf_sucesso() throws Exception {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setCodigo(1);
        entity.setNome("Usuario Identificado");
        entity.setCpf("12345678900");
        entity.setEmail("usuario@email.com");

        when(usuarioRepository.findByCpf("12345678900")).thenReturn(entity);

        ApiResponse<Usuario> response = usuarioService.buscarUsuarioCpf("12345678900");

        assertTrue(response.isSuccess());
        assertEquals("Usuario Identificado", response.getData().getNome());
        verify(usuarioRepository, times(1)).findByCpf("12345678900");
    }

    @Test
    public void testProcessarUsuario_comIdentificacao() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setIdentificar_usuario(true);
        request.setNome("Usuario Identificado Um");
        request.setCpf("99999999999");
        request.setEmail("usuario@email.com");

        UsuarioEntity entity = new UsuarioEntity();
        entity.setCodigo(2);
        entity.setNome("Usuario Identificado Um");
        entity.setCpf("99999999999");
        entity.setEmail("usuario@email.com");
        when(usuarioRepository.save(any())).thenReturn(entity);

        ApiResponse<Usuario> response = usuarioService.processarUsuario(request);

        assertTrue(response.isSuccess());
        assertEquals("Usuario Identificado Um", response.getData().getNome());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    public void testProcessarUsuario_semIdentificacao() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setIdentificar_usuario(false);

        UsuarioEntity entity = new UsuarioEntity();
        entity.setCodigo(3);
        entity.setNome("USUARIO PADRAO");
        entity.setCpf("00000000000");
        entity.setEmail("padrao@email.com");

        when(usuarioRepository.save(any())).thenReturn(entity);

        ApiResponse<Usuario> response = usuarioService.processarUsuario(request);

        assertTrue(response.isSuccess());
        assertEquals("USUARIO PADRAO", response.getData().getNome());
        verify(usuarioRepository, times(1)).save(any());
    }
}
