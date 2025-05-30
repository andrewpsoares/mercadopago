package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.repository.EntregaRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.EntregaDto;
import faculdade.mercadopago.core.domain.dto.ViewEntregaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;


    public ApiResponse<ViewEntregaDto> entregarPedido(EntregaDto entregaDto){
        PedidoEntity pedido = pedidoRepository.getReferenceById(entregaDto.getCodigo());
        pedido.setStatus(entregaDto.getStatus());
        var pedidoSalvo = pedidoRepository.save(pedido);

        EntregaEntity entrega = new EntregaEntity();
        entrega.setPedidoCodigo(pedido);
        entrega.setDataHoraEntrega(LocalDateTime.now());
        var entregaSalva = entregaRepository.save(entrega);

        pedidoService.removerPedidoDaFila(entregaDto.getCodigo());

        var viewEntregaDto = ViewEntregaDto.builder()
                        .DataHoraEntrega(entregaSalva.getDataHoraEntrega())
                        .codigo(entregaSalva.getCodigo())
                        .status(pedidoSalvo.getStatus())
                        .build();

        var apiResponse = new ApiResponse<ViewEntregaDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewEntregaDto);
        return apiResponse;
    }
}
