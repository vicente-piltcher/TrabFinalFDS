package com.bcopstein.sistvendas.dominio.persistencia;

import java.time.LocalDate;
import java.util.List;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;

public interface IOrcamentoRepositorio {
    List<OrcamentoModel> todos();
    OrcamentoModel cadastra(OrcamentoModel orcamento);
    OrcamentoModel recuperaPorId(long id);
    void marcaComoEfetivado(long id); 
    List<OrcamentoModel> recuperaListaDataOrcamento(LocalDate d1, LocalDate d2);
}
