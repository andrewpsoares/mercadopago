package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.EntregaEntity;
import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import faculdade.mercadopago.adapter.driven.repository.CategoriaRepository;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewProdutoDto;
import faculdade.mercadopago.core.domain.dto.ViewCategoriaDto;
import faculdade.mercadopago.core.domain.dto.ViewProdutoDto;
import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ApiResponse<ViewProdutoDto> cadastrarProduto(NewProdutoDto newProdutoDto){
        var produto = new ProdutoEntity();
        produto.setNome(newProdutoDto.getNome());
        var categoria = categoriaRepository.findByCodigo(newProdutoDto.getCategoria());
        produto.setCategoria(categoria);
        produto.setPreco(newProdutoDto.getPreco());
        produto.setDescricao(newProdutoDto.getDescricao());
        produto.setTempopreparo(newProdutoDto.getTempopreparo());

        var produtoEntity = produtoRepository.save(produto);
        var viewProdutoDto = ViewProdutoDto.builder()
                .codigo(produtoEntity.getCodigo())
                .nome(produtoEntity.getNome())
                .preco(produtoEntity.getPreco())
                .descricao(produtoEntity.getDescricao())
                .categoria(produtoEntity.getCategoria().getCodigo())
                .tempopreparo(produtoEntity.getTempopreparo())
                .build();

        var apiResponse = new ApiResponse<ViewProdutoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewProdutoDto);
        return apiResponse;
    }

    public ApiResponse<ViewProdutoDto> atualizarProduto(ViewProdutoDto produtoDto, Long codigo){
        var produto = produtoRepository.getReferenceById(codigo);
        var categoria = categoriaRepository.findByCodigo(codigo);
        produto.setCategoria(categoria);
        produto.setPreco(produtoDto.getPreco());
        produto.setNome(produtoDto.getNome());
        produto.setTempopreparo(produtoDto.getTempopreparo());
        produto.setDescricao(produtoDto.getDescricao());
        produtoRepository.save(produto);

        var viewProdutoDto = ViewProdutoDto.builder()
                .codigo(produto.getCodigo())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .descricao(produto.getDescricao())
                .categoria(produto.getCategoria().getCodigo())
                .tempopreparo(produto.getTempopreparo())
                .build();
        var apiResponse = new ApiResponse<ViewProdutoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewProdutoDto);
        return apiResponse;
    }

    public void removerProduto(Long codigo){
            var produto = produtoRepository.findByCodigo(codigo);
               produtoRepository.delete(produto);
    }

    public ApiResponse<ViewProdutoDto> buscarProduto(Long codigo){
        var produtoEntity = produtoRepository.findByCodigo(codigo);
        var viewProdutoDto = ViewProdutoDto.builder()
                .codigo(produtoEntity.getCodigo())
                .nome(produtoEntity.getNome())
                .preco(produtoEntity.getPreco())
                .descricao(produtoEntity.getDescricao())
                .categoria(produtoEntity.getCategoria().getCodigo())
                .tempopreparo(produtoEntity.getTempopreparo())
                .build();

        var apiResponse = new ApiResponse<ViewProdutoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewProdutoDto);
        return apiResponse;
    }

    public ApiResponse<ViewCategoriaDto> buscarProdutoCategoria(Long codigoCategoria) {
        var categoriaEntity = categoriaRepository.findByCodigo(codigoCategoria);
        var viewCategoriaDto = ViewCategoriaDto.builder()
                .codigo(categoriaEntity.getCodigo())
                .nome(categoriaEntity.getNome())
                .build();

        var apiResponse = new ApiResponse<ViewCategoriaDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewCategoriaDto);
        return apiResponse;
    }
}
