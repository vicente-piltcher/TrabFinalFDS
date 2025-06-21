package com.bcopstein.sistvendas.dominio.modelos;

import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;

public class OrcamentoModel {
    private long id;
    private List<ItemPedidoModel> itens = new LinkedList<>();
    private double custoItens;
    private double imposto;
    private double desconto;
    private double custoConsumidor;
    private boolean efetivado;
    private LocalDate date;

    public OrcamentoModel(long id) {
        this.id = id;
        this.itens = new LinkedList<>();
        this.efetivado = false;
        this.date = LocalDate.now();
    }

    public OrcamentoModel(){
        this.itens = new LinkedList<>();
        this.efetivado = false;
        LocalDate hoje = LocalDate.now();
        this.setDate(hoje);
    }

    public void addItensPedido(ItemPedidoModel item){
        itens.add(item);     
    }

    public void addItensPedido(PedidoModel pedido){
        for(ItemPedidoModel itemPedido:pedido.getItens()){
            itens.add(itemPedido);
        }
    }

    public List<ItemPedidoModel> getItens(){
        return itens;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public double getCustoItens() {
        return custoItens;
    }

    public void setCustoItens(double custoItens){
        this.custoItens = custoItens;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto){
        this.imposto = imposto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto){
        this.desconto = desconto;
    }

    public double getCustoConsumidor() {
        return custoConsumidor;
    }

    public void setCustoConsumidor(double custoConsumidor){
        this.custoConsumidor = custoConsumidor;
    }

    public boolean isEfetivado() {
        return efetivado;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public void setDate(LocalDate d) {
        this.date = d;
    }

    public void efetiva(){
        efetivado = true;
    }
}
