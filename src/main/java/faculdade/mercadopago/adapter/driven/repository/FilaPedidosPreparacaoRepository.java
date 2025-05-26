package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.adapter.driven.entity.FilaPedidosPreparacaoEntity;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilaPedidosPreparacaoRepository extends JpaRepository<FilaPedidosPreparacaoEntity,Long> {
    FilaPedidosPreparacaoEntity findByPedidocodigo(PedidoEntity pedido);
}
