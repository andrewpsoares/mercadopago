package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.adapter.driven.entity.PedidoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PedidoItemRepository extends JpaRepository<PedidoItemEntity, Long> {
}
