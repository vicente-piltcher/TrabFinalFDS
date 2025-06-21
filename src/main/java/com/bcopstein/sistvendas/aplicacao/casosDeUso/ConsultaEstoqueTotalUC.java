package com.bcopstein.sistvendas.aplicacao.casosDeUso;
import java.util.List;
import com.bcopstein.sistvendas.aplicacao.dtos.EstoqueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.dominio.servicos.ServidoDeGerenciamento;


@Component
public class ConsultaEstoqueTotalUC {
        
    private ServidoDeGerenciamento servidoDeGerenciamento;
        
    @Autowired
    public ConsultaEstoqueTotalUC(ServidoDeGerenciamento servidoDeGerenciamento){
            this.servidoDeGerenciamento = servidoDeGerenciamento;
        }
    
    public List<EstoqueDTO> run(){
        return servidoDeGerenciamento.estoquePorProduto();
        }
    }
