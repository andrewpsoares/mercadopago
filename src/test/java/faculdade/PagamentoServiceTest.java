package faculdade;

import faculdade.mercadopago.adapter.driven.entity.PagamentoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.repository.PagamentoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.services.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PagamentoServiceTest {
    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePagamento() throws Exception {
        long codigoPedido = 10;
        BigDecimal valor = BigDecimal.valueOf(15.0);
        var status = "approved";

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigo(codigoPedido);
        pedido.setStatus(StatusPedidoEnum.RECEBIDO);

        PagamentoEntity pagamento = new PagamentoEntity(
                pedido,
                valor,
                status
        );

        when(pedidoRepository.findById(codigoPedido)).thenReturn(Optional.of(pedido));
        when(pagamentoRepository.save(any(PagamentoEntity.class)))
                .thenAnswer(invocation -> {
                    PagamentoEntity entity = invocation.getArgument(0);
                    entity.setId(100L);
                    entity.setPedidoId(pedido);
                    entity.setDataPagamento(LocalDateTime.now());
                    entity.setValor(valor);
                    entity.setStatus(status);
                    return entity;
                });

        boolean response = pagamentoService.CreatePagamento(codigoPedido, valor, status);

        assertTrue(response);
        assertEquals(status, "approved");
    }
}
