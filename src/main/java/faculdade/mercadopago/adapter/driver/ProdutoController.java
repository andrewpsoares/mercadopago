package faculdade.mercadopago.adapter.driver;

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

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/buscar/{codigoProduto}")
    public ResponseEntity<ApiResponse<ViewProdutoDto>> buscarPorCodigoProduto(@PathVariable long codigoProduto) throws Exception {
         var response = produtoService.buscarProduto(codigoProduto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/buscar/categoria/{categoria}")
    public ResponseEntity<ApiResponse<ViewCategoriaDto>> buscarPorCategoriaProduto(@PathVariable long codigoCategoria) {
        var response = produtoService.buscarCategoria(codigoCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/buscar/{categoria}")
    public ResponseEntity<ApiResponse<ViewProdutoDto>> buscarProdutosPorCategoriaProduto(@PathVariable long codigoCategoria) {
        var response = produtoService.buscarProdutosPorCategoria(codigoCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<ViewProdutoDto>> adicionar(@RequestBody NewProdutoDto produto) {
        var response = produtoService.cadastrarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{codigo}")
    @Transactional
    public ResponseEntity removerProduto(@PathVariable Long codigo) throws Exception {
        produtoService.removerProduto(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping("/{codigo}")
    @Transactional
    public ResponseEntity<ApiResponse<ViewProdutoDto>> atualizar(@PathVariable Long codigo, @RequestBody ViewProdutoDto produto) throws Exception {
        var response = produtoService.atualizarProduto(produto, codigo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
