package faculdade;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.repository.EntregaRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.EntregaDto;
import faculdade.mercadopago.core.domain.dto.ViewEntregaDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.EntregaService;
import faculdade.mercadopago.core.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EntregaServiceTest {

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private EntregaService entregaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEntregarPedido() {
        long codigoPedido = 10;
        StatusPedidoEnum status = StatusPedidoEnum.FINALIZADO;
        Date dataEntrega = new Date();

        EntregaDto entregaDto = new EntregaDto();
        entregaDto.setCodigo(codigoPedido);
        entregaDto.setStatus(status);

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigo(codigoPedido);
        pedido.setStatus(status);

        EntregaEntity entregaEntity = new EntregaEntity();
        entregaEntity.setCodigo(10);
        entregaEntity.setPedidoCodigo(pedido);
        entregaEntity.setDataHoraEntrega(dataEntrega);

        when(pedidoRepository.getReferenceById(codigoPedido)).thenReturn(pedido);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        when(entregaRepository.save(any(EntregaEntity.class)))
                .thenAnswer(invocation -> {
                    EntregaEntity entity = invocation.getArgument(0);
                    entity.setCodigo(10);
                    entity.setDataHoraEntrega(new Date());
                    return entity;
                });


        ApiResponse<ViewEntregaDto> response = entregaService.entregarPedido(entregaDto);

        assertEquals(true, response.isSuccess());
        assertEquals(status, response.getData().getStatus());
        assertEquals(10, response.getData().getCodigo());
        verify(pedidoService, times(1)).removerPedidoDaFila(codigoPedido);
    }
}
