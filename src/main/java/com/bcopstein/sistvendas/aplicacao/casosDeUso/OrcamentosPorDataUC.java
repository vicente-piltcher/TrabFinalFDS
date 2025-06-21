package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;


@Component
public class OrcamentosPorDataUC {
        
    private ServicoDeVendas servicoDeVendas;
        
    @Autowired
    public OrcamentosPorDataUC(ServicoDeVendas servicoDeVendas){
            this.servicoDeVendas = servicoDeVendas;
        }
    
    public List<OrcamentoModel> run(String d1, String d2){
        return servicoDeVendas.listaPorData(d1, d2);
        }
    }
