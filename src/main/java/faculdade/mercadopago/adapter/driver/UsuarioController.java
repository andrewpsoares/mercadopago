package faculdade.mercadopago.adapter.driver;


import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.model.Usuario;
import faculdade.mercadopago.core.domain.model.UsuarioRequest;
import faculdade.mercadopago.core.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Identificar usuário", description = "Busca cadastro de cliente já existente")
    @GetMapping
    public ResponseEntity<ApiResponse<Usuario>> buscarUsuario(@RequestParam String cpf) throws Exception {
        var response = usuarioService.buscarUsuarioCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário ou identifica usuário com registro padrão")
    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> criarUsuario(@RequestBody @Valid UsuarioRequest request) throws Exception {
        var response = usuarioService.processarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
