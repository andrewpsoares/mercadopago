package com.example.demo.controllers;

import com.example.demo.enums.StatusEnum;
import com.example.demo.models.Pedido;
import com.example.demo.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @PutMapping("/{codigo}")
    public String alterarStatusPedido(@PathVariable Integer codigo, @RequestBody Map<String, String> requestPayload){
        String status = requestPayload.get("status");
        StatusEnum novoStatus = StatusEnum.valueOf(status);
        pedidoRepository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(novoStatus);
            pedidoRepository.save(pedido);
        });
        return "OK";
    }

    @GetMapping("/pedido")
    public List<Pedido> listarPedidosPorStatus(@RequestParam String status) {
        StatusEnum statusQuery = StatusEnum.valueOf(status.toUpperCase());
        return pedidoRepository.findByStatusWithItens(statusQuery);
    }
}
