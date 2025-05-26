package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.EntregaService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/entregar")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    @Transactional
    public ResponseEntity<EntregaEntity> finalizarPedido(@RequestBody @NotNull Map<String, Object> request){
        Long codigopedido = Long.parseLong(request.get("pedidocodigo").toString());
        var pedidoFinalizado =  entregaService.entregarPedido(codigopedido);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoFinalizado);
    }
}
