package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.core.domain.model.Produto;
import faculdade.mercadopago.adapter.driven.infra.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Produto> buscarPorCategoria(@PathVariable Long categoria) {
        return repository.findByCategoria(categoria);
    }

    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
