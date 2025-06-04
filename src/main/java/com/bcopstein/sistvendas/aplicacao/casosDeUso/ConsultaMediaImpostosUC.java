package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.bcopstein.sistvendas.dominio.servicos.ServidoDeGerenciamento;


@Component
public class ConsultaMediaImpostosUC {
        
    private ServidoDeGerenciamento servidoDeGerenciamento;
        
    @Autowired
    public ConsultaMediaImpostosUC(ServidoDeGerenciamento servidoDeGerenciamento){
            this.servidoDeGerenciamento = servidoDeGerenciamento;
        }
    
    public double run(){
        return servidoDeGerenciamento.mediaDeImpostoPorOrcamento();
        }
    }
