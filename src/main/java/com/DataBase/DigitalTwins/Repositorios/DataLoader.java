package com.DataBase.DigitalTwins.Repositorios;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.DataBase.DigitalTwins.Backend.Classes.Viatura;
import com.DataBase.DigitalTwins.Backend.Classes.Estacao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ViaturaRepository viaturaRepository;
    private final EstacaoRepository estacaoRepository;
    private final JdbcTemplate jdbcTemplate;

    public DataLoader(ViaturaRepository viaturaRepository, 
                      EstacaoRepository estacaoRepository,
                      JdbcTemplate jdbcTemplate) {
        this.viaturaRepository = viaturaRepository;
        this.estacaoRepository = estacaoRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Verificando dados iniciais...");

        // ----- Verificação das Viaturas -----
        Integer viaturaCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Viaturas", Integer.class);
        System.out.println("Viaturas encontradas (via JDBC): " + viaturaCount);

        List<Viatura> viaturas = viaturaRepository.findAll();
        System.out.println("Viaturas encontradas (via JPA): " + viaturas.size());

        if (viaturas.isEmpty()) {
            System.err.println("ERRO: Nenhuma viatura carregada!");
            System.err.println("Verifique:");
            System.err.println("1. O arquivo data.sql está no classpath");
            System.err.println("2. Os valores de enum correspondem exatamente");
            System.err.println("3. As constraints não estão bloqueando a inserção");
        }

        // ----- Verificação das Estações -----
        Integer estacaoCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Estacoes", Integer.class);
        System.out.println("Estações encontradas (via JDBC): " + estacaoCount);

        List<Estacao> estacoes = estacaoRepository.findAll();
        System.out.println("Estações encontradas (via JPA): " + estacoes.size());

        if (estacoes.isEmpty()) {
            System.err.println("ERRO: Nenhuma estação carregada!");
            System.err.println("Verifique:");
            System.err.println("1. O arquivo data.sql está no classpath");
            System.err.println("2. Os valores de enum correspondem exatamente");
            System.err.println("3. As constraints não estão bloqueando a inserção");
        }
    }
}
