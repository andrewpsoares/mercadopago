package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewProdutoDto;
import faculdade.mercadopago.core.domain.dto.ViewProdutoDto;
import faculdade.mercadopago.core.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public ApiResponse<ViewProdutoDto> cadastrarProduto(NewProdutoDto newProdutoDto){
        var produto = new ProdutoEntity();
        produto.setNome(newProdutoDto.getNome());
        produto.setCategoria(newProdutoDto.getCategoria());
        produto.setPreco(newProdutoDto.getPreco());
        produto.setDescricao(newProdutoDto.getDescricao());
        produto.setTempopreparo(newProdutoDto.getTempopreparo());

        var produtoEntity = produtoRepository.save(produto);
        var viewProdutoDto = ViewProdutoDto.builder()
                .codigo(produtoEntity.getCodigo())
                .nome(produtoEntity.getNome())
                .preco(produtoEntity.getPreco())
                .descricao(produtoEntity.getDescricao())
                .categoria(produtoEntity.getCategoria())
                .tempopreparo(produtoEntity.getTempopreparo())
                .build();

        var apiResponse = new ApiResponse<ViewProdutoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewProdutoDto);
        return apiResponse;
    }

    public void atualizarProduto(ViewProdutoDto produtoDto){

    }

    public void deletarProduto(Long codigo){

    }

    public void buscarProduto(Long codigo){

    }
}
