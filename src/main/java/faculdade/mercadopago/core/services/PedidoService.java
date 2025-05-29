package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.*;
import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
import faculdade.mercadopago.core.domain.dto.ViewFilaDto;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.domain.mapper.PedidoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private  PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;


    public ApiResponse<ViewPedidoDto> alterarPedido(long codigo, StatusPedidoEnum status) {
        var pedidoEntity = pedidoRepository.getReferenceById(codigo);
        pedidoEntity.setStatus(status);
        var pedido = pedidoRepository.save(pedidoEntity);


        var viewPedidoDto = ViewPedidoDto.builder()
                            .pedido(pedido.getCodigo())
                            .usuario(pedido.getUsuario().getCodigo())
                            .status(pedido.getStatus())
                            .valorTotal(pedido.getValorTotal())
                            .tempoTotalPreparo(pedido.getTempoTotalPreparo())
                            .dataHoraSolicitacao(pedido.getDataHoraSolicitacao())
                            .build();
        var apiResponse = new ApiResponse<ViewPedidoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewPedidoDto);
        return apiResponse;
    }

    public ApiResponse<List<ViewPedidoDto>> listarPedidos(StatusPedidoEnum status){
        var listaPedidos = pedidoRepository.findAllByStatus(status);
        var listViewPedidoDto = listaPedidos.stream()
                .map(pedidoMapper::entityToDto)
                .toList();
        return ApiResponse.ok(listViewPedidoDto);
    }

    public ApiResponse<ViewPedidoDto> criarPedido(NewPedidoDto dados) {
        PedidoEntity pedido = new PedidoEntity();
        // Identifica Usuário
        UsuarioEntity usuario = usuarioRepository.findById(dados.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: código " + dados.getUsuario()));
        pedido.setUsuario(usuario);

        // Adiciona Status e Data/Hora
        pedido.setStatus(StatusPedidoEnum.RECEBIDO);
        pedido.setDataHoraSolicitacao(LocalDateTime.now());

        // Cria os itens do pedido
        List<PedidoItemEntity> itens = dados.getItens().stream()
                .map(itemDto -> {
                    ProdutoEntity produto = produtoRepository.findById(itemDto.getProdutocodigo())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: código " + itemDto.getProdutocodigo()));

                    return PedidoItemEntity.builder()
                            .pedido(pedido)
                            .produtocodigo(produto)
                            .quantidade(itemDto.getQuantidade())
                            .precounitario(produto.getPreco())
                            .precototal(produto.getPreco().multiply(BigDecimal.valueOf(itemDto.getQuantidade())))
                            .build();
                })
                .toList();

        // Calcula e define os valores do pedido
        pedido.setTempoTotalPreparo(pedido.calcularTempoTotalDePreparo(itens));
        pedido.setValorTotal(pedido.calcularValorTotalPedido(itens));
        pedido.setItens(itens);

        // Persiste o pedido
        PedidoEntity pedidoSalvo = pedidoRepository.save(pedido);

        // Monta DTO de resposta
        ViewPedidoDto viewPedidoDto = ViewPedidoDto.builder()
                .pedido(pedidoSalvo.getCodigo())
                .usuario(pedidoSalvo.getUsuario().getCodigo())
                .status(pedidoSalvo.getStatus())
                .valorTotal(pedidoSalvo.getValorTotal())
                .dataHoraSolicitacao(pedidoSalvo.getDataHoraSolicitacao())
                .tempoTotalPreparo(pedidoSalvo.getTempoTotalPreparo())
                .build();
        ApiResponse<ViewPedidoDto> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewPedidoDto);

        return apiResponse;
    }


    public ApiResponse<ViewFilaDto> adicionarPedidoNaFila(Long codigo){
        var pedido = pedidoRepository.getReferenceById(codigo);
        FilaPedidosPreparacaoEntity pedidoFila = new FilaPedidosPreparacaoEntity();
        pedidoFila.setPedidocodigo(pedido);
        var pedidoIncluso = filaPedidosPreparacaoRepository.save(pedidoFila);

        var viewFilaDto = ViewFilaDto.builder()
                .codigoPedido(pedido.getCodigo())
                .status(pedidoIncluso.getPedidocodigo().getStatus())
                .build();

        var apiResponse = new ApiResponse<ViewFilaDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewFilaDto);
        return apiResponse;
    }

    public void removerPedidoDaFila(Long codigo){
        FilaPedidosPreparacaoEntity pedidoFila = filaPedidosPreparacaoRepository.getReferenceById(codigo);
        filaPedidosPreparacaoRepository.delete(pedidoFila);
    }
}