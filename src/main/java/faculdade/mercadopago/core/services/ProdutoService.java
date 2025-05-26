package faculdade.mercadopago.core.services;

import faculdade.mercadopago.adapter.driven.entity.CategoriaEntity;
import faculdade.mercadopago.adapter.driven.repository.CategoriaRepository;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewProdutoDto;
import faculdade.mercadopago.core.domain.dto.ViewCategoriaDto;
import faculdade.mercadopago.core.domain.dto.ViewProdutoDto;
import faculdade.mercadopago.core.domain.mapper.ProdutoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    public ApiResponse<ViewProdutoDto> cadastrarProduto(NewProdutoDto newProdutoDto){
        var produto = produtoMapper.newDtoToEntity(newProdutoDto);
        var produtoEntity = produtoRepository.save(produto);
        var viewProdutoDto = produtoMapper.entityToDto(produtoEntity);

        var apiResponse = new ApiResponse<ViewProdutoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewProdutoDto);
        return apiResponse;
    }

    public ApiResponse<ViewProdutoDto> atualizarProduto(ViewProdutoDto produtoDto, Long codigo) throws Exception {
        var produtoEntity = produtoRepository.findById(codigo).orElseThrow(() -> new Exception("Produto não encontrado"));
        produtoMapper.updateFromDto(produtoDto, produtoEntity);
        produtoRepository.save(produtoEntity);
        var viewProdutoDto = produtoMapper.entityToDto(produtoEntity);

        var apiResponse = new ApiResponse<ViewProdutoDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewProdutoDto);
        return apiResponse;
    }

    public void removerProduto(Long codigo) throws Exception {
            var produto = produtoRepository.findById(codigo).orElseThrow(() -> new Exception("Produto não encontrado"));
               produtoRepository.delete(produto);
    }

    public ApiResponse<ViewProdutoDto> buscarProduto(Long codigo) throws Exception {
        var produtoEntity = produtoRepository.findById(codigo).orElseThrow(() -> new Exception("Produto não encontrado"));
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

    public ApiResponse<ViewCategoriaDto> buscarCategoria(Long codigoCategoria) {
        var categoriaEntityOptional = categoriaRepository.findById(codigoCategoria);
        var categoriaEntity = new CategoriaEntity();
        if (categoriaEntityOptional.isPresent())
        {
            categoriaEntity = categoriaEntityOptional.get();
        }
        var viewCategoriaDto = ViewCategoriaDto.builder()
                .codigo(categoriaEntity.getCodigo())
                .nome(categoriaEntity.getNome())
                .build();

        var apiResponse = new ApiResponse<ViewCategoriaDto>();
        apiResponse.setSuccess(true);
        apiResponse.setData(viewCategoriaDto);
        return apiResponse;
    }

//    public ApiResponse<List<ViewProdutoDto>> buscarProdutosPorCategoria(long codigoCategoria) {
//        var listaProdutos = produtoRepository.findByCategoriaCodigo(codigoCategoria);
//
//        var apiResponse = new ApiResponse<List<ViewCategoriaDto>>();
//        apiResponse.setSuccess(true);
//        apiResponse.setData(null);
//        return apiResponse;
//    }
}
