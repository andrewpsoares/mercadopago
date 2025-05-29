package faculdade;

import faculdade.mercadopago.adapter.driver.EntregaController;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.EntregaDto;
import faculdade.mercadopago.core.domain.dto.ViewEntregaDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.EntregaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;

import static faculdade.mercadopago.core.domain.enums.StatusPedidoEnum.FINALIZADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EntregaControllerTest {

    @Mock
    private EntregaService entregaService;

    @InjectMocks
    private EntregaController entregaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFinalizarPedido() {
        EntregaDto entregaDto = new EntregaDto();
        entregaDto.setCodigo(1);
        entregaDto.setStatus(StatusPedidoEnum.FINALIZADO);

        ViewEntregaDto viewEntregaDto = new ViewEntregaDto(1L, FINALIZADO, LocalDateTime.now());

        ApiResponse<ViewEntregaDto> responseMock = new ApiResponse<>();
        responseMock.setSuccess(true);
        responseMock.setData(viewEntregaDto);

        when(entregaService.entregarPedido(entregaDto)).thenReturn(responseMock);

        ResponseEntity<ApiResponse<ViewEntregaDto>> response = entregaController.finalizarPedido(entregaDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseMock, response.getBody());
    }
}
