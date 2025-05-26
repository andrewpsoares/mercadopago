package faculdade;

import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.adapter.driver.PedidoController;
import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
import faculdade.mercadopago.core.domain.dto.NewPedidoItemDto;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoControllerTest {
    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPedidosComSucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        StatusPedidoEnum status = StatusPedidoEnum.RECEBIDO;

        ViewPedidoDto pedido1 = new ViewPedidoDto(1L, 10L, status, new BigDecimal("59.90"), LocalDateTime.now(), new Time(1800000));
        ViewPedidoDto pedido2 = new ViewPedidoDto(2L, 11L, status, new BigDecimal("89.90"), LocalDateTime.now(), new Time(2400000));

        Page<ViewPedidoDto> paginaPedidos = new PageImpl<>(List.of(pedido1, pedido2), pageable, 2);

        when(pedidoService.listarPedidos(pageable, status)).thenReturn(paginaPedidos);

        ResponseEntity<Page<ViewPedidoDto>> response = pedidoController.listarPedidos(pageable, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
        assertEquals(1L, response.getBody().getContent().get(0).codigo());
        assertEquals(2L, response.getBody().getContent().get(1).codigo());
    }

    @Test
    void testAlterarStatusDoPedidoComSucesso() {
        Long codigoPedido = 1L;
        Map<String, Object> request = new HashMap<>();
        request.put("status", "EM_PREPARO");

        PedidoEntity pedidoEntity = new PedidoEntity();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setCodigo(2L);

        pedidoEntity.setCodigo(codigoPedido);
        pedidoEntity.setUsuario(usuario);
        pedidoEntity.setStatus(StatusPedidoEnum.EM_PREPARO);
        pedidoEntity.setValortotal(new BigDecimal("100.00"));
        pedidoEntity.setDatahorasolicitacao(LocalDateTime.now());
        pedidoEntity.setTempototalpreparo(new Time(1800000));

        when(pedidoService.alterarPedido(codigoPedido, StatusPedidoEnum.EM_PREPARO))
                .thenReturn(pedidoEntity);

        ResponseEntity<ViewPedidoDto> response = pedidoController.alterarStatusPedido(codigoPedido, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(codigoPedido, response.getBody().codigo());
        assertEquals(StatusPedidoEnum.EM_PREPARO, response.getBody().status());
    }

    @Test
    void testIncluirPedidoComSucesso() {
        NewPedidoItemDto item = new NewPedidoItemDto(1L, 2);
        NewPedidoDto newPedidoDto = new NewPedidoDto(
                10L,
                new Time(1800000),
                List.of(item)
        );

        PedidoEntity pedidoCriado = new PedidoEntity();
        pedidoCriado.setCodigo(1L);
        pedidoCriado.setStatus(StatusPedidoEnum.RECEBIDO);
        pedidoCriado.setValortotal(new BigDecimal("100.00"));
        pedidoCriado.setDatahorasolicitacao(LocalDateTime.now());
        pedidoCriado.setTempototalpreparo(new Time(1800000));

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setCodigo(10L);
        pedidoCriado.setUsuario(usuario);

        when(pedidoService.criarPedido(any(NewPedidoDto.class))).thenReturn(pedidoCriado);

        ResponseEntity<PedidoEntity> response = pedidoController.incluirPedido(newPedidoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getCodigo());
        assertEquals(StatusPedidoEnum.RECEBIDO, response.getBody().getStatus());
        assertEquals(10L, response.getBody().getUsuario().getCodigo());
    }

    @Test
    void testRemoverPedidoDaFilaDePreparoComSucesso() {
        Long codigoPedido = 1L;
        ResponseEntity<Void> response = pedidoController.removerPedidoDaFilaDePreparo(codigoPedido);
        verify(pedidoService).removerPedidoDaFila(codigoPedido);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
