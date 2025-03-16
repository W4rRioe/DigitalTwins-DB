package com.Repositorios;

import com.Backend.Classes.Viatura;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViaturaRepository extends JpaRepository<Viatura, Long> {
    Optional<Viatura> findByMatricula(String matricula);
}
