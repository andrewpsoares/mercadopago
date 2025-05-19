package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.core.domain.dto.AlterarPedidoDto;
import faculdade.mercadopago.core.domain.dto.ListarPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.PedidoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<ListarPedidoDto>> listarPedidos(Pageable pageable, @RequestParam StatusPedidoEnum status){
        var lista = pedidoService.listarPedidos(pageable, status);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{CODIGO}")
    @Transactional
    public ResponseEntity alterarStatusPedido(@PathVariable Long CODIGO, @RequestBody @Valid AlterarPedidoDto dados){
        var pedido = pedidoService.alterarPedido(CODIGO, dados);
        return ResponseEntity.ok(new ListarPedidoDto(pedido));
    }
}
