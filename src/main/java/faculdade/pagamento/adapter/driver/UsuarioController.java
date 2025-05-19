package faculdade.pagamento.adapter.driver;


import faculdade.pagamento.core.domain.UsuarioRequest;
import faculdade.pagamento.core.domain.model.Usuario;
import faculdade.pagamento.core.services.UsuarioService;
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

    private UsuarioService usuarioService;

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
