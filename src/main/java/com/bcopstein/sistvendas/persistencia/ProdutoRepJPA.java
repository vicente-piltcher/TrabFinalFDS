package com.bcopstein.sistvendas.persistencia;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;

@Repository
@Primary
public class ProdutoRepJPA implements IProdutoRepositorio {
    private ProdutoJPA_ItfRep produtoRepository;

    @Autowired
    public ProdutoRepJPA(ProdutoJPA_ItfRep produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoModel> todos() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.size() == 0) {
            return new LinkedList<ProdutoModel>();
        } else {
            return produtos.stream()
                    .map(prod -> Produto.toProdutoModel(prod))
                    .toList();
        }
    }

    public ProdutoModel consultaPorId(long id) {
        Produto produto = produtoRepository.findById(id);
        System.out.println("Produto de codigo: "+id+": "+produto);
        if (produto == null) {
            return null;
        } else {
            return Produto.toProdutoModel(produto);
        }
    }
}
