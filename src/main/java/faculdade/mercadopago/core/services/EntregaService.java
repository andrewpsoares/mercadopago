package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.repository.EntregaRepository;
import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.domain.dto.CriarPedidoDto;
import faculdade.mercadopago.core.domain.dto.EntregaDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;

    public EntregaEntity entregarPedido(EntregaDto dados){
        var pedido = pedidoRepository.getReferenceById(dados.pedidocodigo());
        pedido.setStatus(StatusPedidoEnum.FINALIZADO);
        pedidoRepository.save(pedido);


        EntregaEntity entrega = new EntregaEntity();
        entrega.setPedidoCodigo(pedido);
        entrega.setDataHoraEntrega(dados.datahoraentrega());
        return entregaRepository.save(entrega);
    }
}
