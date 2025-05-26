package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewProdutoDto;
import faculdade.mercadopago.core.domain.dto.ViewCategoriaDto;
import faculdade.mercadopago.core.domain.dto.ViewProdutoDto;
import faculdade.mercadopago.core.services.ProdutoService;
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

    // ✅ Buscar produto por código
    @GetMapping("/buscar/produto/{codigoProduto}")
    public ResponseEntity<ApiResponse<ViewProdutoDto>> buscarPorCodigoProduto(@PathVariable long codigoProduto) throws Exception {
        var response = produtoService.buscarProduto(codigoProduto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ Buscar categoria por código
    @GetMapping("/buscar/categoria/{codigoCategoria}")
    public ResponseEntity<ApiResponse<ViewCategoriaDto>> buscarPorCategoriaProduto(@PathVariable long codigoCategoria) {
        var response = produtoService.buscarCategoria(codigoCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ Buscar produtos por categoria
    @GetMapping("/buscar/categoria/{codigoCategoria}/produtos")
    public ResponseEntity<ApiResponse<ViewCategoriaDto>> buscarProdutosPorCategoriaProduto(@PathVariable long codigoCategoria) {
        var response = produtoService.buscarProdutosPorCategoria(codigoCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ Adicionar produto
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<ViewProdutoDto>> adicionar(@RequestBody NewProdutoDto produto) {
        var response = produtoService.cadastrarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Remover produto
    @DeleteMapping("/{codigo}")
    @Transactional
    public ResponseEntity<Void> removerProduto(@PathVariable Long codigo) throws Exception {
        produtoService.removerProduto(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ✅ Atualizar produto
    @PutMapping("/{codigo}")
    @Transactional
    public ResponseEntity<ApiResponse<ViewProdutoDto>> atualizar(@PathVariable Long codigo, @RequestBody ViewProdutoDto produto) throws Exception {
        var response = produtoService.atualizarProduto(produto, codigo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
