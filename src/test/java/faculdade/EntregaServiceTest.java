package faculdade;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.repository.EntregaRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.EntregaDto;
import faculdade.mercadopago.core.domain.dto.ViewEntregaDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.domain.mapper.EntregaMapper;
import faculdade.mercadopago.core.services.EntregaService;
import faculdade.mercadopago.core.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class EntregaServiceTest {

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private EntregaMapper entregaMapper;

    @InjectMocks
    private EntregaService entregaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEntregarPedido() {
        EntregaDto entregaDto = new EntregaDto();
        entregaDto.setCodigo(1L);
        entregaDto.setStatus(StatusPedidoEnum.FINALIZADO);

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigo(1L);
        pedido.setStatus(StatusPedidoEnum.PRONTO);

        PedidoEntity pedidoAtualizado = new PedidoEntity();
        pedidoAtualizado.setCodigo(1L);
        pedidoAtualizado.setStatus(StatusPedidoEnum.FINALIZADO);

        EntregaEntity entrega = new EntregaEntity();
        entrega.setPedidoCodigo(pedidoAtualizado);
        entrega.setDataHoraEntrega(LocalDateTime.now());

        EntregaEntity entregaSalva = new EntregaEntity();
        entregaSalva.setPedidoCodigo(pedidoAtualizado);
        entregaSalva.setDataHoraEntrega(entrega.getDataHoraEntrega());

        ViewEntregaDto viewEntregaDto = new ViewEntregaDto();
        viewEntregaDto.setStatus(StatusPedidoEnum.FINALIZADO);

        when(pedidoRepository.getReferenceById(1L)).thenReturn(pedido);
        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoAtualizado);
        when(entregaRepository.save(any(EntregaEntity.class))).thenReturn(entregaSalva);
        when(entregaMapper.entityToDto(entregaSalva)).thenReturn(viewEntregaDto);

        // Act
        ApiResponse<ViewEntregaDto> response = entregaService.entregarPedido(entregaDto);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals(StatusPedidoEnum.FINALIZADO, response.getData().getStatus());

        verify(pedidoRepository).getReferenceById(1L);
        verify(pedidoRepository).save(pedido);
        verify(entregaRepository).save(any(EntregaEntity.class));
        verify(pedidoService).removerPedidoDaFila(1L);
        verify(entregaMapper).entityToDto(entregaSalva);
    }
}
