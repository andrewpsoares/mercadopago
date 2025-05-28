package faculdade;

import faculdade.mercadopago.adapter.driver.PedidoController;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        // Arrange
        StatusPedidoEnum status = StatusPedidoEnum.RECEBIDO;

        List<ViewPedidoDto> pedidosMock = List.of(
                new ViewPedidoDto(1L, 10L, StatusPedidoEnum.RECEBIDO, BigDecimal.valueOf(50), LocalDateTime.now(), Time.valueOf("00:10:00")),
                new ViewPedidoDto(2L, 11L, StatusPedidoEnum.RECEBIDO, BigDecimal.valueOf(80), LocalDateTime.now(), Time.valueOf("00:15:00"))
        );

        ApiResponse<List<ViewPedidoDto>> apiResponse = ApiResponse.ok(pedidosMock);

        when(pedidoService.listarPedidos(status)).thenReturn(apiResponse);

        // Act
        ResponseEntity<ApiResponse<List<ViewPedidoDto>>> response = pedidoController.listarPedidos(status);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(2, response.getBody().getData().size());
        assertEquals(status, response.getBody().getData().get(0).getStatus());

        verify(pedidoService, times(1)).listarPedidos(status);
    }

    @Test
    void testAlterarStatusDoPedidoComSucesso() {
        long codigo = 1L;
        Map<String, String> request = Map.of("status", "RECEBIDO");

        ApiResponse<ViewPedidoDto> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        ViewPedidoDto dto = ViewPedidoDto.builder()
                .pedido(codigo)
                .status(StatusPedidoEnum.RECEBIDO)
                .build();
        apiResponse.setData(dto);

        when(pedidoService.alterarPedido(codigo, StatusPedidoEnum.RECEBIDO)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse<ViewPedidoDto>> response = pedidoController.alterarStatusPedido(codigo, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(codigo, response.getBody().getData().getPedido());
        verify(pedidoService, times(1)).alterarPedido(codigo, StatusPedidoEnum.RECEBIDO);
    }

    @Test
    void testIncluirPedidoComSucesso() {
        NewPedidoDto newPedidoDto = new NewPedidoDto();

        ViewPedidoDto viewPedidoDto = ViewPedidoDto.builder()
                .pedido(1L)
                .usuario(10L)
                .status(StatusPedidoEnum.RECEBIDO)
                .valorTotal(new BigDecimal("100.00"))
                .tempoTotalPreparo(null)
                .dataHoraSolicitacao(LocalDateTime.now())
                .build();

        ApiResponse<ViewPedidoDto> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewPedidoDto);

        when(pedidoService.criarPedido(newPedidoDto)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse<ViewPedidoDto>> response = pedidoController.incluirPedido(newPedidoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(viewPedidoDto.getPedido(), response.getBody().getData().getPedido());

        verify(pedidoService, times(1)).criarPedido(newPedidoDto);
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
