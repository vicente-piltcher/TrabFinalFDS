package com.bcopstein.sistvendas.persistencia;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrcamentoJPA_ItfRep extends CrudRepository<Orcamento,Long>{
    @SuppressWarnings("null")
    List<Orcamento> findAll();
    Optional<Orcamento> findById(long id);

    @Query("SELECT o FROM Orcamento o WHERE o.date BETWEEN :d1 AND :d2 AND o.efetivado = true")
    List<Orcamento> findOrcamentoBDates(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2);

}