package com.example.demo.core.services;

import com.example.demo.adapter.driven.infra.PedidoRepository;
import com.example.demo.core.domain.dto.AlterarPedidoDto;
import com.example.demo.core.domain.dto.ListarPedidoDto;
import com.example.demo.core.domain.enums.StatusPedidoEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public void alterarPedido(Long codigo, @Valid AlterarPedidoDto dados) {
        var pedido = pedidoRepository.getReferenceById(codigo);
        pedido.alterarStatusPedido(dados);
    }

    public Page<ListarPedidoDto> listarPedidos(Pageable pageable, StatusPedidoEnum status){
        return pedidoRepository.findAllBySTATUS(pageable, status).map(ListarPedidoDto::new);
    }
}
