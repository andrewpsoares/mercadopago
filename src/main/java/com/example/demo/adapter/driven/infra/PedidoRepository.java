package com.example.demo.adapter.driven.infra;

import com.example.demo.core.domain.enums.StatusPedidoEnum;
import com.example.demo.core.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    Page<Pedido> findAllBySTATUS(Pageable pageable, StatusPedidoEnum status);
}
