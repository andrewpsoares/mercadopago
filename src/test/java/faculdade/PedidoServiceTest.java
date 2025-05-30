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
import faculdade.mercadopago.core.domain.mapper.FilaPedidosPreparacaoMapper;
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
    private FilaPedidosPreparacaoMapper filaPedidosPreparacaoMapper;

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
        long codigo = 123L;
        StatusPedidoEnum novoStatus = StatusPedidoEnum.EM_PREPARO;

        PedidoEntity pedidoExistente = new PedidoEntity();
        pedidoExistente.setCodigo(codigo);
        pedidoExistente.setStatus(StatusPedidoEnum.RECEBIDO);

        PedidoEntity pedidoAtualizado = new PedidoEntity();
        pedidoAtualizado.setCodigo(codigo);
        pedidoAtualizado.setStatus(novoStatus);

        ViewPedidoDto viewPedidoDto = new ViewPedidoDto();
        viewPedidoDto.setPedido(codigo);
        viewPedidoDto.setStatus(novoStatus);

        when(pedidoRepository.getReferenceById(codigo)).thenReturn(pedidoExistente);
        when(pedidoRepository.save(pedidoExistente)).thenReturn(pedidoAtualizado);
        when(pedidoMapper.entityToDto(pedidoAtualizado)).thenReturn(viewPedidoDto);

        // Act
        ApiResponse<ViewPedidoDto> response = pedidoService.alterarPedido(codigo, novoStatus);

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(codigo, response.getData().getPedido());
        assertEquals(novoStatus, response.getData().getStatus());

        verify(pedidoRepository).getReferenceById(codigo);
        verify(pedidoRepository).save(pedidoExistente);
        verify(pedidoMapper).entityToDto(pedidoAtualizado);

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
        Long usuarioId = 1L;
        Long produtoId = 10L;

        NewPedidoItemDto itemDto = new NewPedidoItemDto();
        itemDto.setProdutocodigo(produtoId);
        itemDto.setQuantidade(2);

        NewPedidoDto newPedidoDto = new NewPedidoDto();
        newPedidoDto.setUsuario(usuarioId);
        newPedidoDto.setItens(List.of(itemDto));

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setCodigo(usuarioId);

        ProdutoEntity produto = new ProdutoEntity();
        produto.setCodigo(produtoId);
        produto.setPreco(new BigDecimal("10.00"));
        produto.setTempopreparo(Time.valueOf("00:10:00"));

        PedidoEntity pedidoSalvo = new PedidoEntity();
        pedidoSalvo.setCodigo(100L);

        ViewPedidoDto viewPedidoDto = new ViewPedidoDto();
        viewPedidoDto.setPedido(100L);

        // Mocks
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoSalvo);
        when(pedidoMapper.entityToDto(pedidoSalvo)).thenReturn(viewPedidoDto);

        ApiResponse<ViewPedidoDto> response = pedidoService.criarPedido(newPedidoDto);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(100L, response.getData().getPedido());

        verify(usuarioRepository).findById(usuarioId);
        verify(produtoRepository).findById(produtoId);
        verify(pedidoRepository).save(any(PedidoEntity.class));
        verify(pedidoMapper).entityToDto(pedidoSalvo);
    }

    @Test
    void testAdicionarPedidoNaFilaComSucesso() {
        Long codigo = 1L;

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setCodigo(codigo);

        FilaPedidosPreparacaoEntity filaEntity = new FilaPedidosPreparacaoEntity();
        filaEntity.setPedidocodigo(pedidoEntity);

        ViewFilaDto viewFilaDto = new ViewFilaDto();
        viewFilaDto.setCodigoPedido(codigo);

        when(pedidoRepository.getReferenceById(codigo)).thenReturn(pedidoEntity);
        when(filaPedidosPreparacaoRepository.save(any(FilaPedidosPreparacaoEntity.class))).thenReturn(filaEntity);
        when(filaPedidosPreparacaoMapper.entityToDto(filaEntity)).thenReturn(viewFilaDto);

        // Act
        ApiResponse<ViewFilaDto> response = pedidoService.adicionarPedidoNaFila(codigo);

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(codigo, response.getData().getCodigoPedido());

        verify(pedidoRepository).getReferenceById(codigo);
        verify(filaPedidosPreparacaoRepository).save(any(FilaPedidosPreparacaoEntity.class));
        verify(filaPedidosPreparacaoMapper).entityToDto(filaEntity);
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