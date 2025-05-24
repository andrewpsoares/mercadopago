package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilaPedidosPreparacaoRepository extends JpaRepository<FilaPedidosPreparacaoEntity,Long> {

}
