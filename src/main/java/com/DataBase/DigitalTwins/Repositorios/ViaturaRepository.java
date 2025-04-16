package com.DataBase.DigitalTwins.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;

@Repository
public interface ViaturaRepository extends JpaRepository<Viatura, Long> {
    Optional<Viatura> findByMatricula(String matricula);
}
