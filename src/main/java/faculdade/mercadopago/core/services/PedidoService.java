package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.domain.dto.AlterarPedidoDto;
import faculdade.mercadopago.core.domain.dto.ListarPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoEntity alterarPedido(Long codigo, @Valid AlterarPedidoDto dados) {
        var pedido = pedidoRepository.getReferenceById(codigo);
        pedido.alterarStatusPedido(dados);
        return pedido;
    }

    public Page<ListarPedidoDto> listarPedidos(Pageable pageable, StatusPedidoEnum status){
        return pedidoRepository.findAllBySTATUS(pageable, status).map(ListarPedidoDto::new);
    }
}