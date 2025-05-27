package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.EntregaDto;
import faculdade.mercadopago.core.domain.dto.ViewEntregaDto;
import faculdade.mercadopago.core.services.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entregar")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Operation(summary = "Finalizar Pedido", description = "Rota responsável por finalizar o pedido e retirar da fila")
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<ViewEntregaDto>> finalizarPedido(@RequestBody EntregaDto entregaDto) {
        var response =  entregaService.entregarPedido(entregaDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
