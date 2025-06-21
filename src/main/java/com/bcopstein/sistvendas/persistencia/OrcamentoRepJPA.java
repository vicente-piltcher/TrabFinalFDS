package com.bcopstein.sistvendas.persistencia;

import java.time.LocalDate;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;

import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;


@Repository
@Primary
public class OrcamentoRepJPA implements IOrcamentoRepositorio
   {
    @Autowired
    private OrcamentoJPA_ItfRep orcamentos;

    @Override
    public List<OrcamentoModel> todos() {
        return orcamentos.findAll().stream()
                  .map(o->Orcamento.toOrcamentoModel(o))
                  .toList();
    }

    @Override
    public OrcamentoModel cadastra(OrcamentoModel orcamento) {
        Orcamento orc = orcamentos.save(Orcamento.fromOrcamentoModel(orcamento));
        return Orcamento.toOrcamentoModel(orc);
        
    }

    @Override
    public List<OrcamentoModel> recuperaListaDataOrcamento(LocalDate d1, LocalDate d2) {
        return orcamentos.findOrcamentoBDates(d1, d2).stream()
        .map(Orcamento::toOrcamentoModel)
        .toList();
    }

    @Override
    public OrcamentoModel recuperaPorId(long id) {
        Orcamento orcamento = orcamentos.findById(id).orElse(null);
        if (orcamento == null) {
            return null;
        }
        return Orcamento.toOrcamentoModel(orcamento);
    }

    @Override
    public void marcaComoEfetivado(long id) {
        Orcamento orcamento = orcamentos.findById(id).orElse(null);
        if (orcamento != null) {
            orcamento.efetiva();
            orcamentos.save(orcamento);
        }
        System.out.println("\n\n- efetivado: " + id);

    }
}
