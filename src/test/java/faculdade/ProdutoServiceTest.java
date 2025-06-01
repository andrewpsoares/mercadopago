package faculdade;

import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import faculdade.mercadopago.adapter.driven.repository.ProdutoRepository;
import faculdade.mercadopago.core.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

class ProdutoServiceTest {

    @Autowired
    private ProdutoEntity produtoEntity;

    @Autowired
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;


    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(ProdutoService.class);
    }

    @Test
    void testCadastrarProdutoComSucesso() {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome();
        produto.setCodigo();
        produto.setCategoria();
        produto.setPreco();
        produto.setDescricao();
        produto.setTempopreparo();
    }

    @Test
    void atualizarProduto() {
    }

    @Test
    void removerProduto() {
    }

    @Test
    void buscarProduto() {
    }

    @Test
    void buscarCategoria() {
    }

    @Test
    void buscarProdutosPorCategoria() {
    }
}