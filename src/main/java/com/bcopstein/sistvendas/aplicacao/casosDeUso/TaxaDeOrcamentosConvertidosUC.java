package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.bcopstein.sistvendas.dominio.servicos.ServidoDeGerenciamento;


@Component
public class TaxaDeOrcamentosConvertidosUC {
        
    private ServidoDeGerenciamento servidoDeGerenciamento;
        
    @Autowired
    public TaxaDeOrcamentosConvertidosUC(ServidoDeGerenciamento servidoDeGerenciamento){
            this.servidoDeGerenciamento = servidoDeGerenciamento;
        }
    
    public double run(){
        return servidoDeGerenciamento.taxaDeOrcamentosConvertidos();
        }
    }
