package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewProdutoDto;
import faculdade.mercadopago.core.domain.dto.ViewCategoriaDto;
import faculdade.mercadopago.core.domain.dto.ViewProdutoDto;
import faculdade.mercadopago.core.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Buscar produto por codigo", description = "Retorna um produto especifico cadastrado no codigo")
    @GetMapping("/buscar/produto/{codigoProduto}")
    public ResponseEntity<ApiResponse<ViewProdutoDto>> buscarPorCodigoProduto(@PathVariable long codigoProduto) throws Exception {
        var response = produtoService.buscarProduto(codigoProduto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar por categoria", description = "Retorna uma categoria especifica do codigo enviado")
    @GetMapping("/buscar/categoria/{codigoCategoria}")
    public ResponseEntity<ApiResponse<ViewCategoriaDto>> buscarPorCategoriaProduto(@PathVariable long codigoCategoria) {
        var response = produtoService.buscarCategoria(codigoCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Busca lista de produtos do codigo de categoria", description = "Retorna lista de produtos especificos do codigo categoria enviado")
    @GetMapping("/buscar/categoria/{codigoCategoria}/produtos")
    public ResponseEntity<ApiResponse<List<ViewProdutoDto>>> buscarProdutosPorCategoriaProduto(@PathVariable long codigoCategoria) {
        var response = produtoService.buscarProdutosPorCategoria(codigoCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Cadastrar novo produto", description = "Inclusão de novo produto no banco de dados")
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<ViewProdutoDto>> adicionar(@RequestBody NewProdutoDto produto) {
        var response = produtoService.cadastrarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Remover produto cadastrado", description = "Remover produto cadastrado do banco de dados")
    @DeleteMapping("/{codigo}")
    @Transactional
    public ResponseEntity<Void> removerProduto(@PathVariable Long codigo) throws Exception {
        produtoService.removerProduto(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar informações de produto", description = "Atualiza informações do produto cadastrado no banco de dados")
    @PutMapping("/{codigo}")
    @Transactional
    public ResponseEntity<ApiResponse<ViewProdutoDto>> atualizar(@PathVariable Long codigo, @RequestBody ViewProdutoDto produto) throws Exception {
        var response = produtoService.atualizarProduto(produto, codigo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
