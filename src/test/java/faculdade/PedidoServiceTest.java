//package faculdade;
//
//import faculdade.mercadopago.adapter.driven.entity.*;
//import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
//import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
//import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
//import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
//import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
//import faculdade.mercadopago.core.domain.dto.NewPedidoItemDto;
//import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
//import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
//import faculdade.mercadopago.core.services.PedidoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.math.BigDecimal;
//import java.sql.Time;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class PedidoServiceTest {
//    @InjectMocks
//    private PedidoService pedidoService;
//
//    @Mock
//    private UsuarioRepository usuarioRepository;
//
//    @Mock
//    private ProdutoRepository produtoRepository;
//
//    @Mock
//    private PedidoRepository pedidoRepository;
//
//    @Mock
//    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testAlterarPedidoComSucesso() {
//        Long codigo = 1L;
//        StatusPedidoEnum novoStatus = StatusPedidoEnum.EM_PREPARO;
//
//        PedidoEntity pedidoMock = mock(PedidoEntity.class);
//        when(pedidoRepository.getReferenceById(codigo)).thenReturn(pedidoMock);
//
//        PedidoEntity resultado = pedidoService.alterarPedido(codigo, novoStatus);
//
//        verify(pedidoMock).alterarStatusPedido(novoStatus);
//        verify(pedidoRepository).save(pedidoMock);
//        assertEquals(pedidoMock, resultado);
//    }
//
//    @Test
//    void testListarPedidosComSucesso() {
//        Time tempoPreparo = new Time(1800000);  // 30 minutos em milissegundos
//
//        Pageable pageable = PageRequest.of(0, 10);
//        StatusPedidoEnum status = StatusPedidoEnum.RECEBIDO;
//
//        PedidoEntity pedido1 = mock(PedidoEntity.class);
//        PedidoEntity pedido2 = mock(PedidoEntity.class);
//        when(pedido1.getTempototalpreparo()).thenReturn(tempoPreparo);
//        when(pedido2.getTempototalpreparo()).thenReturn(tempoPreparo);
//
//        UsuarioEntity usuarioMock1 = mock(UsuarioEntity.class);
//        when(usuarioMock1.getCodigo()).thenReturn(100L);
//        when(pedido1.getUsuario()).thenReturn(usuarioMock1);
//
//        UsuarioEntity usuarioMock2 = mock(UsuarioEntity.class);
//        when(usuarioMock2.getCodigo()).thenReturn(101L);
//        when(pedido2.getUsuario()).thenReturn(usuarioMock2);
//
//        List<PedidoEntity> pedidos = List.of(pedido1, pedido2);
//        Page<PedidoEntity> pagePedidos = new PageImpl<>(pedidos, pageable, pedidos.size());
//
//        when(pedidoRepository.findAllByStatus(pageable, status)).thenReturn(pagePedidos);
//
//        Page<ViewPedidoDto> resultado = pedidoService.listarPedidos(pageable, status);
//
//        assertNotNull(resultado);
//        assertEquals(pedidos.size(), resultado.getContent().size());
//        resultado.getContent().forEach(Objects::requireNonNull);
//        verify(pedidoRepository).findAllByStatus(pageable, status);
//    }
//
//    @Test
//    void testCriarPedidoComSucesso() {
//        NewPedidoItemDto itemDto = new NewPedidoItemDto(1L, 2); // produto id = 1, quantidade = 2
//        NewPedidoDto newPedidoDto = new NewPedidoDto(100L, List.of(itemDto));
//
//        UsuarioEntity usuario = new UsuarioEntity();
//        usuario.setCodigo(100L);
//
//        ProdutoEntity produto = new ProdutoEntity();
//        produto.setCodigo(1L);
//        produto.setPreco(new BigDecimal("50.00"));
//        produto.setTempopreparo(new Time(1800000));
//
//
//        PedidoItemEntity item = new PedidoItemEntity();
//        item.setQuantidade(2);
//        item.setProdutocodigo(produto);
//        item.setPrecounitario(produto.getPreco());
//        item.setPrecototal(item.calcularPrecoTotalItem());
//
//        PedidoEntity pedido = new PedidoEntity();
//        pedido.setUsuario(usuario);
//        pedido.setStatus(StatusPedidoEnum.RECEBIDO);
//        pedido.setDataHoraSolicitacao(LocalDateTime.now());
//        pedido.setTempototalpreparo(new Time(1800000));
//        pedido.setItens(List.of(item));
//        pedido.setValortotal(new BigDecimal("100.00"));
//
//        when(usuarioRepository.findById(100L)).thenReturn(Optional.of(usuario));
//        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
//        when(pedidoRepository.save(any(PedidoEntity.class))).thenAnswer(invocation -> {
//            PedidoEntity saved = invocation.getArgument(0);
//            saved.setItens(List.of(item)); // garante que o retorno do mock tenha itens
//            return saved;
//        });
//
//        // Act
//        PedidoEntity resultado = pedidoService.criarPedido(newPedidoDto);
//
//        // Assert
//        assertNotNull(resultado);
//        assertEquals(StatusPedidoEnum.RECEBIDO, resultado.getStatus());
//        assertEquals(1, resultado.getItens().size());
//        assertEquals(new BigDecimal("100.00"), resultado.getValortotal());
//    }
//
//    @Test
//    void testAdicionarPedidoNaFilaComSucesso() {
//        Long codigoPedido = 1L;
//
//        PedidoEntity pedidoMock = new PedidoEntity();
//        pedidoMock.setCodigo(codigoPedido);
//
//        FilaPedidosPreparacaoEntity filaMock = new FilaPedidosPreparacaoEntity();
//        filaMock.setPedidocodigo(pedidoMock);
//
//        when(pedidoRepository.getReferenceById(codigoPedido)).thenReturn(pedidoMock);
//        when(filaPedidosPreparacaoRepository.save(any(FilaPedidosPreparacaoEntity.class))).thenReturn(filaMock);
//
//        // Act
//        FilaPedidosPreparacaoEntity resultado = pedidoService.adicionarPedidoNaFila(codigoPedido);
//
//        // Assert
//        assertNotNull(resultado);
//        assertEquals(codigoPedido, resultado.getPedidocodigo().getCodigo());
//
//        verify(pedidoRepository).getReferenceById(codigoPedido);
//        verify(filaPedidosPreparacaoRepository).save(any(FilaPedidosPreparacaoEntity.class));
//    }
//
//    @Test
//    void testRemoverPedidoDaFilaComSucesso() {
//        Long codigo = 1L;
//
//        FilaPedidosPreparacaoEntity pedidoFila = new FilaPedidosPreparacaoEntity();
//        pedidoFila.setCodigo(codigo);
//
//        when(filaPedidosPreparacaoRepository.getReferenceById(codigo)).thenReturn(pedidoFila);
//
//        // Act
//        pedidoService.removerPedidoDaFila(codigo);
//
//        // Assert
//        verify(filaPedidosPreparacaoRepository).getReferenceById(codigo);
//        verify(filaPedidosPreparacaoRepository).delete(pedidoFila);
//    }
//}