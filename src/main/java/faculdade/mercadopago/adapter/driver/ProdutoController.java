package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.core.applications.ports.ApiResponse;
import faculdade.mercadopago.core.domain.dto.NewProdutoDto;
import faculdade.mercadopago.core.domain.dto.ViewProdutoDto;
import faculdade.mercadopago.core.services.ProdutoService;
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

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ProdutoEntity> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/categoria/{categoria}")
    public List<ProdutoEntity> buscarPorCategoria(@PathVariable Long categoria) {
        return repository.findByCategoria(categoria);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ViewProdutoDto>> adicionar(@RequestBody NewProdutoDto produto) {
        var response = produtoService.cadastrarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ProdutoEntity> atualizar(@PathVariable Long codigo, @RequestBody ViewProdutoDto produto) {
        if (!repository.existsById(codigo)) {
            return ResponseEntity.notFound().build();
        }
        produto.setCodigo(codigo);
        ProdutoEntity atualizado = repository.save(produto);
        return ResponseEntity.ok(atualizado);
    }
}
