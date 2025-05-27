package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoItemEntity;
import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.core.domain.dto.CriarPedidoDto;
import faculdade.mercadopago.core.domain.dto.ListarPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;
    @Autowired
    private ProdutoMapper produtoMapper;


    public ApiResponse<ViewPedidoDto> alterarPedido(long codigo, StatusPedidoEnum status) {
        var pedidoEntity = pedidoRepository.getReferenceById(codigo);
        pedidoEntity.setStatus(status);
        pedidoRepository.save(pedidoEntity);

        var viewPedidoDto = produtoMapper.entityToDto(pedidoEntity);

        var apiResponse = new ApiResponse<ViewPedidoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewPedidoDto);
        return apiResponse;
    }

    public ApiResponse<List<ViewPedidoDto>> listarPedidos(StatusPedidoEnum status){
        var listaPedidos = pedidoRepository.findAllByStatus(status);
        var listViewPedidoDto = listaPedidos.stream()
                .map(PedidoMapper::entityToDto)
                .toList();
        return ApiResponse.ok(listViewPedidoDto);
    }

    public ApiResponse<ViewPedidoDto> criarPedido(NewPedidoDto dados){
        var usuario = usuarioRepository.findById(dados.usuariocodigo())
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
        PedidoEntity pedido = new PedidoEntity();
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedidoEnum.RECEBIDO);
        pedido.setDatahorasolicitacao(LocalDateTime.now());

        //Cria os itens do pedido
        List<PedidoItemEntity> itens = dados.itens().stream().map(
                itemDto ->{
                    PedidoItemEntity item = new PedidoItemEntity();
                    var produto = produtoRepository.findById(itemDto.produtocodigo())
                            .orElseThrow(()-> new RuntimeException("Produto não encontrado"));
                    item.setPedido(pedido);
                    item.setProdutocodigo(produto);
                    item.setQuantidade(itemDto.quantidade());
                    item.setPrecounitario(produto.getPreco());
                    item.setPrecototal(item.calcularPrecoTotalItem());
                    return item;
                }).toList();

        //Set valor total e itens
        pedido.setTempototalpreparo(pedido.calcularTempoTotalDePreparo(itens));
        pedido.setValortotal(pedido.calcularValorTotalPedido(itens));
        pedido.setItens(itens);

        return  pedidoRepository.save(pedido);
    }

    public FilaPedidosPreparacaoEntity adicionarPedidoNaFila(Long codigo){
        var pedido = pedidoRepository.getReferenceById(codigo);
        FilaPedidosPreparacaoEntity pedidoFila = new FilaPedidosPreparacaoEntity();
        pedidoFila.setPedidocodigo(pedido);
        return filaPedidosPreparacaoRepository.save(pedidoFila);
    }

    public void removerPedidoDaFila(Long codigo){
        FilaPedidosPreparacaoEntity pedidoFila = filaPedidosPreparacaoRepository.getReferenceById(codigo);
        filaPedidosPreparacaoRepository.delete(pedidoFila);
    }
}