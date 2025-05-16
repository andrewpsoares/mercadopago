package com.example.demo.controllers;

import com.example.demo.enums.StatusEnum;
import com.example.demo.models.Pedido;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.services.AlterarStatusService;
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

    @Autowired
    private AlterarStatusService service;

    @PutMapping("/{codigo}")
    public ResponseEntity<String> alterarStatusPedido(@PathVariable Integer codigo, @RequestBody Map<String, String> requestPayload){
        return service.alterarStatus(codigo,requestPayload);
    }

    @GetMapping("/pedido")
    public List<Pedido> listarPedidosPorStatus(@RequestParam String status) {
        return service.listarPedidosPorStatus(status);
    }

}
