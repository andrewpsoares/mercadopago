package faculdade.mercadopago.adapter.driven.repository;


import faculdade.mercadopago.adapter.driven.entity.CategoriaEntity;
import faculdade.mercadopago.adapter.driven.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    CategoriaEntity findByCodigo(long codigo);
}
