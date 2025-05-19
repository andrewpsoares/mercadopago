package com.example.demo.adapter.driver;

import com.example.demo.core.domain.dto.AlterarPedidoDto;
import com.example.demo.core.domain.dto.ListarPedidoDto;
import com.example.demo.core.services.PedidoService;
import com.example.demo.core.domain.enums.StatusPedidoEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
