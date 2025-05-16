package com.example.demo.services;

import com.example.demo.enums.StatusEnum;
import com.example.demo.models.Pedido;
import com.example.demo.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AlterarStatusService {


    private final PedidoRepository pedidoRepository;

    @Autowired
    public AlterarStatusService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public ResponseEntity<String> alterarStatus(@PathVariable Integer codigo, @RequestBody Map<String, String> requestPayload){
        String status = requestPayload.get("status");
        StatusEnum novoStatus;
        try {
            novoStatus = parseStatus(status);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status inválido: " + status);
        }

        Optional<Pedido> pedidoOptional = pedidoRepository.findById(codigo);

        if (pedidoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado: " + codigo);
        }

        Pedido pedido = pedidoOptional.get();
        pedido.setStatus(novoStatus);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok("Status atualizado para " + novoStatus);
    }

    public List<Pedido> listarPedidosPorStatus(String status) {
        StatusEnum statusQuery = parseStatus(status);
        return pedidoRepository.findByStatusWithItens(statusQuery);
    }

    private StatusEnum parseStatus(String status) {
        try {
            return StatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status não encontrado: " + status);
        }
    }
}
