package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.dominio.servicos.ServicoDeEstoque;


@Component
public class AdicionaEstoqueUC {
        
    private ServicoDeEstoque servicoDeEstoque;
        
    @Autowired
    public AdicionaEstoqueUC(ServicoDeEstoque servicoDeEstoque){
            this.servicoDeEstoque = servicoDeEstoque;
        }
    
    public void run(long idOrcamento, int qtd){
        servicoDeEstoque.adicionaEstoque(idOrcamento, qtd);
        }
    }
