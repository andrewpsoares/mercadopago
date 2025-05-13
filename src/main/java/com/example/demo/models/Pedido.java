package com.example.demo.models;

import com.example.demo.enums.StatusEnum;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String cliente;
    private double valorTotal;
    private OffsetDateTime dataHoraSolicitacao;
    private int tempoTotalPreparo;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PedidoItem> itens;


    public void calcularTempoPreparo(){
        this.tempoTotalPreparo = itens.stream()
                .mapToInt(PedidoItem::getTempoPreparo)
                .sum();
    }

    public void calcularValorTotalPedido(){
        this.valorTotal = itens.stream()
                .mapToDouble(PedidoItem::getPrecoTotal)
                .sum();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public OffsetDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(OffsetDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public int getTempoTotalPreparo() {
        return tempoTotalPreparo;
    }

    public void setTempoTotalPreparo(int tempoTotalPreparo) {
        this.tempoTotalPreparo = tempoTotalPreparo;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }

    public void addItem(PedidoItem item) {
        item.setPedido(this);
        this.itens.add(item);
        calcularTempoPreparo();
    }

    public void removeItem(PedidoItem item) {
        this.itens.remove(item);
        calcularTempoPreparo();
    }
}
