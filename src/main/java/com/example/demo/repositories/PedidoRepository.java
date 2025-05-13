package com.example.demo.repositories;

import com.example.demo.enums.StatusEnum;
import com.example.demo.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.status = :status")
    List<Pedido> findByStatusWithItens(@Param("status") StatusEnum status);
}
