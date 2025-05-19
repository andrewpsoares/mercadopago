package faculdade.mercadopago.adapter.driven.infra;

import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
import faculdade.mercadopago.core.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    Page<Pedido> findAllBySTATUS(Pageable pageable, StatusPedidoEnum status);
}
