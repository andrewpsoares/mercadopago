package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.repository.EntregaRepository;
import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;

    public EntregaEntity entregarPedido(Long codigopedido){
        var pedido = pedidoRepository.getReferenceById(codigopedido);
        pedido.alterarStatusPedido(StatusPedidoEnum.FINALIZADO);
        pedidoRepository.save(pedido);

        EntregaEntity entrega = new EntregaEntity();
        entrega.setPedidocodigo(pedido);
        entrega.setDatahoraentrega(LocalDateTime.now());
        return entregaRepository.save(entrega);
    }
}
