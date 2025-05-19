package faculdade.mercadopago.adapter.driven.infra;

import faculdade.mercadopago.core.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByCategoria(String categoria);
}
