package faculdade;

import faculdade.mercadopago.adapter.driver.UsuarioController;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.model.Usuario;
import faculdade.mercadopago.core.domain.model.UsuarioRequest;
import faculdade.mercadopago.core.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarUsuarioCpf() throws Exception {
        String cpf = "12345678900";

        Usuario usuario = Usuario.builder()
                .codigo(1)
                .nome("Teste 1")
                .cpf(cpf)
                .email("teste1@email.com")
                .build();

        ApiResponse<Usuario> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(usuario);

        when(usuarioService.buscarUsuarioCpf(cpf)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse<Usuario>> response = usuarioController.buscarUsuario(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(cpf, response.getBody().getData().getCpf());
    }

    @Test
    void testCriarUsuario() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setCpf("12345678900");
        request.setNome("Teste 1");
        request.setEmail("teste1@email.com");
        request.setIdentificar_usuario(true);

        Usuario usuario = Usuario.builder()
                .codigo(1)
                .nome("Teste 1")
                .cpf(request.getCpf())
                .email(request.getEmail())
                .build();

        ApiResponse<Usuario> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(usuario);

        when(usuarioService.processarUsuario(request)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse<Usuario>> response = usuarioController.criarUsuario(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(request.getCpf(), response.getBody().getData().getCpf());
    }

    @Test
    void testCriarUsuarioPadrao() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setCpf("00000000000");
        request.setNome("USUARIO PADRAO");
        request.setEmail("padrao@email.com");
        request.setIdentificar_usuario(true);

        Usuario usuario = Usuario.builder()
                .codigo(1)
                .nome("USUARIO PADRAO")
                .cpf(request.getCpf())
                .email(request.getEmail())
                .build();

        ApiResponse<Usuario> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(usuario);

        when(usuarioService.processarUsuario(request)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse<Usuario>> response = usuarioController.criarUsuario(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(request.getCpf(), response.getBody().getData().getCpf());
    }
}
