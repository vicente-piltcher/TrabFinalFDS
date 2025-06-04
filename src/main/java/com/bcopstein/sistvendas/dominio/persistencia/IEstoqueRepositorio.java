package com.bcopstein.sistvendas.dominio.persistencia;

import java.util.List;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;

public interface IEstoqueRepositorio {
    List<ProdutoModel> todos();
    List<ProdutoModel> todosComEstoque();
    int quantidadeEmEstoque(long codigo);
    int baixaEstoque(long codProd, int qtdade);
    int adicionaEstoque(long codProd, int qtd);
}
