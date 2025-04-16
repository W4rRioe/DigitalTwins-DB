package com.DataBase.DigitalTwins.Repositorios;

import com.DataBase.DigitalTwins.Backend.Classes.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Long> {
    Optional<Estacao> findByNome(String nome);
    List<Estacao> findByLatitudeBetweenAndLongitudeBetween(Double latMin, Double latMax, Double lonMin, Double lonMax);
}
