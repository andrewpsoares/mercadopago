package faculdade.mercadopago.adapter.driver;

import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
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
    public List<ProdutoEntity> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/categoria/{categoria}")
    public List<ProdutoEntity> buscarPorCategoria(@PathVariable Long categoria) {
        return repository.findByCategoria(categoria);
    }

    @PostMapping
    public ProdutoEntity adicionar(@RequestBody ProdutoEntity produto) {
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
