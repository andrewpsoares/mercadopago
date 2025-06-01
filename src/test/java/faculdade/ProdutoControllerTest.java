package faculdade;

import faculdade.mercadopago.adapter.driver.ProdutoController;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewProdutoDto;
import faculdade.mercadopago.core.domain.dto.ViewCategoriaDto;
import faculdade.mercadopago.core.domain.dto.ViewProdutoDto;
import faculdade.mercadopago.core.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorCodigoProduto() throws Exception {
        long codigo = 10;
        ViewProdutoDto dto = ViewProdutoDto.builder()
                .codigo(codigo)
                .nome("Produto")
                .build();

        ApiResponse<ViewProdutoDto> response = ApiResponse.ok(dto);

        when(produtoService.buscarProduto(codigo)).thenReturn(response);

        var result = produtoController.buscarPorCodigoProduto(codigo);
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().isSuccess());
        assertEquals(codigo, result.getBody().getData().getCodigo());
    }

    @Test
    void buscarPorCategoriaProduto() {
        long codigoCategoria = 5;
        ViewCategoriaDto categoriaDto = ViewCategoriaDto.builder()
                .codigo(codigoCategoria)
                .nome("Categoria")
                .build();

        ApiResponse<ViewCategoriaDto> response = ApiResponse.ok(categoriaDto);

        when(produtoService.buscarCategoria(codigoCategoria)).thenReturn(response);

        var result = produtoController.buscarPorCategoriaProduto(codigoCategoria);
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().isSuccess());
        assertEquals(codigoCategoria, result.getBody().getData().getCodigo());
    }

    @Test
    void buscarProdutosPorCategoriaProduto() {
        long codigoCategoria = 7;

        ViewProdutoDto produto1 = ViewProdutoDto.builder().codigo(1L).nome("Produto 1").categoria(codigoCategoria).build();
        ViewProdutoDto produto2 = ViewProdutoDto.builder().codigo(2L).nome("Produto 2").categoria(codigoCategoria).build();

        List<ViewProdutoDto> lista = List.of(produto1, produto2);
        ApiResponse<List<ViewProdutoDto>> response = ApiResponse.ok(lista);

        when(produtoService.buscarProdutosPorCategoria(codigoCategoria)).thenReturn(response);

        var result = produtoController.buscarProdutosPorCategoriaProduto(codigoCategoria);
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().isSuccess());
        assertEquals(2, result.getBody().getData().size());
    }

    @Test
    void adicionar() {
        NewProdutoDto novoProduto = new NewProdutoDto();
        novoProduto.setNome("Produto 1");
        novoProduto.setDescricao("Produto Teste 1");
        novoProduto.setCategoria(1);
        novoProduto.setPreco(BigDecimal.TEN);
        novoProduto.setTempopreparo(new Time(300000));

        ViewProdutoDto produtoSalvo = ViewProdutoDto.builder()
                .codigo(1)
                .nome(novoProduto.getNome())
                .descricao(novoProduto.getDescricao())
                .categoria(novoProduto.getCategoria())
                .preco(novoProduto.getPreco())
                .tempopreparo(novoProduto.getTempopreparo())
                .build();

        ApiResponse<ViewProdutoDto> response = ApiResponse.ok(produtoSalvo);

        when(produtoService.cadastrarProduto(novoProduto)).thenReturn(response);

        var result = produtoController.adicionar(novoProduto);

        assertEquals(201, result.getStatusCodeValue());
        assertTrue(result.getBody().isSuccess());
        assertEquals(produtoSalvo.getCodigo(), result.getBody().getData().getCodigo());
    }

    @Test
    void removerProduto() throws Exception {
        long codigo = 5;

        doNothing().when(produtoService).removerProduto(codigo);

        var response = produtoController.removerProduto(codigo);

        assertEquals(204, response.getStatusCodeValue());
        verify(produtoService, times(1)).removerProduto(codigo);
    }

    @Test
    void atualizar() throws Exception {
        long codigo = 6;

        ViewProdutoDto produtoAtualizado = ViewProdutoDto.builder()
                .codigo(codigo)
                .nome("Produto Atualizado")
                .descricao("Novo Produto Atualizado")
                .categoria(4)
                .preco(BigDecimal.valueOf(123))
                .tempopreparo(new Time(500000))
                .build();

        ApiResponse<ViewProdutoDto> response = ApiResponse.ok(produtoAtualizado);

        when(produtoService.atualizarProduto(produtoAtualizado, codigo)).thenReturn(response);

        var result = produtoController.atualizar(codigo, produtoAtualizado);

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().isSuccess());
        assertEquals(codigo, result.getBody().getData().getCodigo());
    }
}
