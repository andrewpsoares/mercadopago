package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoItemEntity;
import faculdade.mercadopago.adapter.driven.entity.UsuarioEntity;
import faculdade.mercadopago.adapter.driven.repository.FilaPedidosPreparacaoRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoItemRepository;
import faculdade.mercadopago.adapter.driven.repository.PedidoRepository;
import faculdade.mercadopago.adapter.driven.repository.UsuarioRepository;
import faculdade.mercadopago.core.domain.dto.AlterarPedidoDto;
import faculdade.mercadopago.core.domain.dto.CriarPedidoDto;
import faculdade.mercadopago.core.domain.dto.ListarPedidoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import faculdade.mercadopago.core.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


    @Autowired
    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;

    @Autowired
    private  PedidoItemRepository pedidoItemRepository;

    public PedidoEntity alterarPedido(Long codigo, @Valid AlterarPedidoDto dados) {
        var pedido = pedidoRepository.getReferenceById(codigo);
        pedido.alterarStatusPedido(dados);
        return pedido;
    }

    public Page<ListarPedidoDto> listarPedidos(Pageable pageable, StatusPedidoEnum status){
        return pedidoRepository.findAllByStatus(pageable, status).map(ListarPedidoDto::new);
    }

    public PedidoEntity criarPedido(CriarPedidoDto dados){

        PedidoEntity pedido = new PedidoEntity();
        System.out.println(dados);
        List<PedidoItemEntity> itens = dados.itens().stream().map(
                itemDto ->{
                    PedidoItemEntity item = new PedidoItemEntity();
                    item.setPedido(pedido);
                    item.setProdutoCodigo(itemDto.produtocodigo());
                    item.setQuantidade(itemDto.quantidade());
                    item.setPrecoUnitario(itemDto.precounitario());
                    item.setPrecoTotal(itemDto.precototal());
                    return  item;
                }).toList();

        pedido.setUsuario(dados.usuariocodigo());
        pedido.setStatus(StatusPedidoEnum.RECEBIDO);
        pedido.setValortotal(dados.valortotal());
        pedido.setDatahorasolicitacao(dados.datahorasolicitacao());
        pedido.setTempototalpreparo(dados.tempototalpreparo());
        pedido.setItens(itens);
        return  pedidoRepository.save(pedido);
    }

    // adicionar pedido na fila (param codigo pedido)
    public FilaPedidosPreparacaoEntity adicionarPedidoNaFila(Long codigo){
        var pedido = pedidoRepository.getReferenceById(codigo);
        FilaPedidosPreparacaoEntity pedidoFila = new FilaPedidosPreparacaoEntity();
        pedidoFila.setPedidocodigo(pedido);
        return filaPedidosPreparacaoRepository.save(pedidoFila);
    }
}