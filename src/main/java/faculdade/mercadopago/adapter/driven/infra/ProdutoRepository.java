package faculdade.mercadopago.adapter.driven.infra;

import faculdade.mercadopago.core.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoria(long categoria);
}
