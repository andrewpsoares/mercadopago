package com.example.demo.controllers;

import com.example.demo.dto.AlterarPedidoDto;
import com.example.demo.dto.ListarPedidoDto;
import com.example.demo.enums.StatusPedidoEnum;
import com.example.demo.repositories.PedidoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public Page<ListarPedidoDto> listarPedidos(Pageable pageable, @RequestParam StatusPedidoEnum status){
        return pedidoRepository.findAllBySTATUS(pageable, status).map(ListarPedidoDto::new);
    }

    @PutMapping("/{CODIGO}")
    @Transactional
    public void alterarStatusPedido(@PathVariable Long CODIGO, @RequestBody @Valid AlterarPedidoDto dados){
        var pedido = pedidoRepository.getReferenceById(CODIGO);
        pedido.alterarStatusPedido(dados);
    }
}
