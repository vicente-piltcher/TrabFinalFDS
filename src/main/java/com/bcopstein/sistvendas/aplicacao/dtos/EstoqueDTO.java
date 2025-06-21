package com.bcopstein.sistvendas.aplicacao.dtos;

import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;

public class EstoqueDTO {
    private long id;
    private int qtdade;

    public EstoqueDTO(long id, int qtdade) {
        this.id = id;
        this.qtdade = qtdade;
    }

    public long getId() {
        return this.id;
    }

    public int getQtdade() {
        return this.qtdade;
    }

    public static EstoqueDTO fromModel(ItemDeEstoqueModel estoque){
        return new EstoqueDTO(estoque.getId(), estoque.getQuantidade());
    }
}

