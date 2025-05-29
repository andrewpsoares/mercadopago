package faculdade;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
import faculdade.mercadopago.core.domain.dto.NewPedidoItemDto;
import faculdade.mercadopago.core.domain.dto.ViewFilaDto;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.domain.mapper.PedidoMapper;
import faculdade.mercadopago.core.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAlterarPedidoComSucesso() {
        long codigoPedido = 1L;
        StatusPedidoEnum novoStatus = StatusPedidoEnum.RECEBIDO;

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setCodigo(10L);

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setCodigo(codigoPedido);
        pedidoEntity.setUsuario(usuario);
        pedidoEntity.setStatus(StatusPedidoEnum.EM_PREPARO);
        pedidoEntity.setValorTotal(BigDecimal.valueOf(30.50));
        pedidoEntity.setTempoTotalPreparo(Time.valueOf("00:09:00"));
        pedidoEntity.setDataHoraSolicitacao(LocalDateTime.now());

        when(pedidoRepository.getReferenceById(codigoPedido)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(any(PedidoEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ApiResponse<ViewPedidoDto> response = pedidoService.alterarPedido(codigoPedido, novoStatus);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(codigoPedido, response.getData().getPedido());
        assertEquals(novoStatus, response.getData().getStatus());
        assertEquals(usuario.getCodigo(), response.getData().getUsuario());

        verify(pedidoRepository).getReferenceById(codigoPedido);
        verify(pedidoRepository).save(pedidoEntity);

    }

    @Test
    void testListarPedidosComSucesso() {
        StatusPedidoEnum status = StatusPedidoEnum.RECEBIDO;

        PedidoEntity pedido1 = new PedidoEntity();
        pedido1.setCodigo(1L);
        PedidoEntity pedido2 = new PedidoEntity();
        pedido2.setCodigo(2L);

        List<PedidoEntity> pedidos = List.of(pedido1, pedido2);

        ViewPedidoDto dto1 = ViewPedidoDto.builder().pedido(1L).build();
        ViewPedidoDto dto2 = ViewPedidoDto.builder().pedido(2L).build();


        when(pedidoRepository.findAllByStatus(status)).thenReturn(pedidos);
        when(pedidoMapper.entityToDto(pedido1)).thenReturn(dto1);
        when(pedidoMapper.entityToDto(pedido2)).thenReturn(dto2);

        ApiResponse<List<ViewPedidoDto>> response = pedidoService.listarPedidos(status);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals(1L, response.getData().get(0).getPedido());
        assertEquals(2L, response.getData().get(1).getPedido());

        verify(pedidoRepository).findAllByStatus(status);
        verify(pedidoMapper).entityToDto(pedido1);
        verify(pedidoMapper).entityToDto(pedido2);
    }

    @Test
    void testCriarPedidoComSucesso() {
        long usuarioId = 1L;
        long produtoId = 10L;
        BigDecimal precoProduto = new BigDecimal("10.00");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setCodigo(usuarioId);

        ProdutoEntity produto = new ProdutoEntity();
        produto.setCodigo(produtoId);
        produto.setPreco(precoProduto);
        produto.setTempopreparo(Time.valueOf("00:09:00"));

        NewPedidoItemDto itemDto = new NewPedidoItemDto();
        itemDto.setProdutocodigo(produtoId);
        itemDto.setQuantidade(2);

        NewPedidoDto newPedidoDto = new NewPedidoDto();
        newPedidoDto.setUsuario(usuarioId);
        newPedidoDto.setItens(List.of(itemDto));

        PedidoEntity pedidoSalvo = new PedidoEntity();
        pedidoSalvo.setCodigo(100L);
        pedidoSalvo.setUsuario(usuario);
        pedidoSalvo.setStatus(StatusPedidoEnum.RECEBIDO);
        pedidoSalvo.setValorTotal(precoProduto.multiply(BigDecimal.valueOf(2)));
        pedidoSalvo.setDataHoraSolicitacao(LocalDateTime.now());
        pedidoSalvo.setTempoTotalPreparo(Time.valueOf("00:18:00"));

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        System.out.println(usuarioId);
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(Mockito.any(PedidoEntity.class))).thenReturn(pedidoSalvo);

        ApiResponse<ViewPedidoDto> response = pedidoService.criarPedido(newPedidoDto);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(pedidoSalvo.getCodigo(), response.getData().getPedido());
        assertEquals(pedidoSalvo.getUsuario().getCodigo(), response.getData().getUsuario());
        assertEquals(pedidoSalvo.getStatus(), response.getData().getStatus());
        assertEquals(pedidoSalvo.getValorTotal(), response.getData().getValorTotal());
        assertEquals(pedidoSalvo.getTempoTotalPreparo(), response.getData().getTempoTotalPreparo());
    }

    @Test
    void testAdicionarPedidoNaFilaComSucesso() {
        Long codigoPedido = 1L;

        PedidoEntity pedidoMock = new PedidoEntity();
        pedidoMock.setCodigo(codigoPedido);

        FilaPedidosPreparacaoEntity filaMock = new FilaPedidosPreparacaoEntity();
        filaMock.setPedidocodigo(pedidoMock);

        when(pedidoRepository.getReferenceById(codigoPedido)).thenReturn(pedidoMock);
        when(filaPedidosPreparacaoRepository.save(any(FilaPedidosPreparacaoEntity.class))).thenReturn(filaMock);

        ApiResponse<ViewFilaDto> resultado = pedidoService.adicionarPedidoNaFila(codigoPedido);

        assertNotNull(resultado);
        assertEquals(codigoPedido, resultado.getData().getCodigoPedido());

        verify(pedidoRepository).getReferenceById(codigoPedido);
        verify(filaPedidosPreparacaoRepository).save(any(FilaPedidosPreparacaoEntity.class));
    }

    @Test
    void testRemoverPedidoDaFilaComSucesso() {
        Long codigo = 1L;

        FilaPedidosPreparacaoEntity pedidoFila = new FilaPedidosPreparacaoEntity();
        pedidoFila.setCodigo(codigo);

        when(filaPedidosPreparacaoRepository.getReferenceById(codigo)).thenReturn(pedidoFila);

        pedidoService.removerPedidoDaFila(codigo);

        verify(filaPedidosPreparacaoRepository).getReferenceById(codigo);
        verify(filaPedidosPreparacaoRepository).delete(pedidoFila);
    }
}