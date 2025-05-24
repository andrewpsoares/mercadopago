package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.repository.EntregaRepository;
import faculdade.mercadopago.core.domain.dto.EntregaDto;
import faculdade.mercadopago.core.services.EntregaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @PostMapping
    @Transactional
    public ResponseEntity finalizarPedido(@RequestBody @Valid EntregaDto dados){
        var pedidoFinalizado =  entregaService.entregarPedido(dados);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoFinalizado);
    }
}
