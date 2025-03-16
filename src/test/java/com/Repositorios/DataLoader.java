package com.Repositorios;

import com.Backend.Classes.Viatura;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ViaturaRepository viaturaRepository;

    public DataLoader(ViaturaRepository viaturaRepository) {
        this.viaturaRepository = viaturaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Viatura> viaturas = viaturaRepository.findAll(); 
        
        if (viaturas.isEmpty()) {
            System.out.println("⚠️ Nenhuma viatura encontrada no banco de dados.");
        } else {
            System.out.println("✅ Viaturas carregadas da base de dados:");
            viaturas.forEach(v -> System.out.println("🚍 " + v.getMatricula() + " - " + v.getStatusOperacional()));
        }
    }
}
