package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServidoDeGerenciamento;


@Component
public class ConsultaProdutosVendidosUC {
        
    private ServidoDeGerenciamento servidoDeGerenciamento;
        
    @Autowired
    public ConsultaProdutosVendidosUC(ServidoDeGerenciamento servidoDeGerenciamento){
            this.servidoDeGerenciamento = servidoDeGerenciamento;
        }
    
    public Map<ProdutoModel, Integer> run(){
        return servidoDeGerenciamento.quantidadeDeProdutosVendidos();
        }
    }
