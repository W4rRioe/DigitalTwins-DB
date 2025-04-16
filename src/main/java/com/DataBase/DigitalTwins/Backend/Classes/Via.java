package com.DataBase.DigitalTwins.Backend.Classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Via {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da via é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O sentido da via é obrigatório")
    @Column(nullable = false)
    private String sentido;

    @Min(value = 10, message = "O limite de velocidade deve ser pelo menos 10 km/h")
    @Max(value = 150, message = "O limite de velocidade não pode ser superior a 150 km/h")
    @Column(nullable = false)
    private int limiteVelocidade;

    @ElementCollection
    @CollectionTable(name = "via_infraestruturas", joinColumns = @JoinColumn(name = "via_id"))
    @Column(name = "infraestrutura")
    private List<String> infraestruturas;

    @Column(nullable = false)
    private boolean congestionamento;

    @Column(name = "evento_de_transito", length = 1000)
    private String eventoDeTransito;

    @Column(name = "condicoes_meteorologicas", length = 500)
    private String condicoesMeteorologicas;

    @Min(value = 0, message = "O tempo médio de viagem não pode ser negativo")
    @Column(name = "tempo_viagem_medio", nullable = false)
    private double tempoViagemMedio;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Version
    private Long versao;
}