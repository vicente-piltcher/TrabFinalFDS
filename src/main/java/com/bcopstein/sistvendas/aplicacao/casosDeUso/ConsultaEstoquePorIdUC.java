package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServidoDeGerenciamento;


@Component
public class ConsultaEstoquePorIdUC {
        
    private ServidoDeGerenciamento servidoDeGerenciamento;
        
    @Autowired
    public ConsultaEstoquePorIdUC(ServidoDeGerenciamento servidoDeGerenciamento){
            this.servidoDeGerenciamento = servidoDeGerenciamento;
        }
    
    public Map<ProdutoModel, Integer> run(long id){
        return servidoDeGerenciamento.estoquePorIdDeProduto(id);
        }
    }
