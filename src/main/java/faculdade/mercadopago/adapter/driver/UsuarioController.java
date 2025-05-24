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
    public ResponseEntity<Usuario> buscarUsuario(@RequestParam String cpf) {
        Usuario usuario = usuarioService.buscarUsuarioCpf(cpf);
        return ResponseEntity.ok(usuario);
    }


    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid UsuarioRequest request) {
        Usuario usuario = usuarioService.processarUsuario(request);
            return ResponseEntity.ok(usuario);
    }
}
