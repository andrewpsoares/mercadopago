package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    List<ProdutoEntity> findByCategoriaCodigo(long categoria);
}
