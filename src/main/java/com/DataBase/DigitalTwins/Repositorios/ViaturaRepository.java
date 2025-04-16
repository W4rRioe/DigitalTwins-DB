package com.DataBase.DigitalTwins.Repositorios;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViaturaRepository extends JpaRepository<Viatura, Long> {
    Optional<Viatura> findByMatricula(String matricula);
    List<Viatura> findByStatusOperacional(Viatura.StatusOperacional status);
}