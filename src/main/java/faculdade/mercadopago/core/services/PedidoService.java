package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoItemEntity;
import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import faculdade.mercadopago.core.domain.dto.NewPedidoDto;
import faculdade.mercadopago.core.domain.dto.ViewPedidoDto;
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


    public PedidoEntity alterarPedido(Long codigo, StatusPedidoEnum status) {
        var pedido = pedidoRepository.getReferenceById(codigo);
        pedido.alterarStatusPedido(status);
        pedidoRepository.save(pedido);
        return pedido;
    }

    public Page<ViewPedidoDto> listarPedidos(Pageable pageable, StatusPedidoEnum status){
        return pedidoRepository.findAllByStatus(pageable, status).map(ViewPedidoDto::new);
    }

    public PedidoEntity criarPedido(NewPedidoDto dados){
        //Pedido pré montado
        var usuario = usuarioRepository.findById(dados.usuariocodigo())
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
        PedidoEntity pedido = new PedidoEntity();
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedidoEnum.RECEBIDO);
        pedido.setDatahorasolicitacao(LocalDateTime.now());
        pedido.setTempototalpreparo(dados.tempototalpreparo());

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
                    return  item;
                }).toList();

        //Set valor total e itens
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