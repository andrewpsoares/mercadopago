package faculdade.mercadopago.adapter.driven.repository;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.adapter.driven.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity,Long> {
    Page<PedidoEntity> findAllBySTATUS(Pageable pageable, StatusPedidoEnum status);
}
