package com.exemplo.lanchonete.repository;

import com.exemplo.lanchonete.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByCategoria(String categoria);
}
