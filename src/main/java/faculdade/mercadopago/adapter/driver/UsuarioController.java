package faculdade.mercadopago.adapter.driver;


import faculdade.mercadopago.core.domain.model.UsuarioRequest;
import faculdade.mercadopago.core.domain.model.Usuario;
import faculdade.mercadopago.core.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> buscarUsuario(@RequestParam String cpf) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioCpf(cpf);

        try {
            return ResponseEntity.ok().body(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não possui cadastro.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody @Valid UsuarioRequest request) {

        try {
            Usuario usuario = usuarioService.processarUsuario(request);
            return ResponseEntity.ok("Usuário cadastrado com sucesso: " + usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
