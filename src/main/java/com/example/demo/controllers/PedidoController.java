package com.example.demo.controllers;

import com.example.demo.models.Pedido;
import com.example.demo.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido) {
        pedido.setDataHoraSolicitacao(OffsetDateTime.now());
        pedido.getItens().forEach(item -> item.setPrecoTotal(item.getPrecoUnitario(), item.getQuantidade()));
        pedido.setValorTotal();
        pedido.calcularTempoPreparo();

        return pedidoRepository.save(pedido);
    }

    @GetMapping("/{id}")
    public Pedido obterPedido(@PathVariable Integer id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
}
