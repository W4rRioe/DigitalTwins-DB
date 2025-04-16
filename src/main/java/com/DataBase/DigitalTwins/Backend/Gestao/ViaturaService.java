package com.DataBase.DigitalTwins.Backend.Gestao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Repositorios.ViaturaRepository;

@Service
public class ViaturaService {

    @Autowired
    private ViaturaRepository viaturaRepository;

    public Viatura getViaturaByMatricula(String matricula) {
        return viaturaRepository.findByMatricula(matricula).orElse(null);
    }
}
