package com.example.demo.repositories;

import com.example.demo.enums.StatusPedidoEnum;
import com.example.demo.models.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    Page<Pedido> findAllBySTATUS(Pageable pageable, StatusPedidoEnum status);
}
