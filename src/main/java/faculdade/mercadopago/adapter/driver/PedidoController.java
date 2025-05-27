package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedido")
public class PedidoController {


    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar pedidos por status", description = "Lista um pedido a partir de um dos status pré definidos")
    public ResponseEntity<ApiResponse<List<ViewPedidoDto>>> listarPedidos(@RequestParam StatusPedidoEnum status){
        var lista = pedidoService.listarPedidos(status);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("/{codigo}")
    @Transactional
    @Operation(summary = "Alterar o status do pedido", description = "Altera o status de um pedido com base no código do pedido e status desejado")
    public ResponseEntity<ApiResponse<ViewPedidoDto>> alterarStatusPedido(@PathVariable long codigo, StatusPedidoEnum status){
        var response = pedidoService.alterarPedido(codigo, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Incluir pedido", description = "Registra um pedido no sistema")
    public ResponseEntity<ApiResponse<ViewPedidoDto>> incluirPedido(@RequestBody @Valid NewPedidoDto dados){
        var response = pedidoService.criarPedido(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{codigoPedido}")
    @Transactional
    @Operation(summary = "Remover pedido da fila", description = "Remove um pedido da fila de preparo com base no código do item na fila")
    public ResponseEntity<Void> removerPedidoDaFilaDePreparo(@PathVariable Long codigoPedido){
        pedidoService.removerPedidoDaFila(codigoPedido);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
