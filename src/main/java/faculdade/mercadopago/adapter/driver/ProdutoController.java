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

    @GetMapping("/buscar/{codigoProduto}")
    public ResponseEntity<ApiResponse<ViewProdutoDto>> buscarPorCodigoProduto(@PathVariable Long codigoProduto) {
         var response = produtoService.buscarProduto(codigoProduto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/buscar/{categoria}")
    public ResponseEntity<ApiResponse<ViewCategoriaDto>> buscarPorCategoriaProduto(@PathVariable Long codigoCategoria) {
        var response = produtoService.buscarProdutoCategoria(codigoCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ViewProdutoDto>> adicionar(@RequestBody NewProdutoDto produto) {
        var response = produtoService.cadastrarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{codigo}")
    @Transactional
    public ResponseEntity removerProduto(@PathVariable Long codigo){
        produtoService.removerProduto(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ApiResponse<ViewProdutoDto>> atualizar(@PathVariable Long codigo, @RequestBody ViewProdutoDto produto) {
        var response = produtoService.atualizarProduto(produto, codigo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
