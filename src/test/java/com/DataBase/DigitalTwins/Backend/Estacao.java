package com.DataBase.DigitalTwins.Backend;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "estacoes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da estação é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "A localização é obrigatória")
    @Column(nullable = false)
    private String localizacao;
    
    @Min(value = 0, message = "A capacidade não pode ser negativa")
    @Column(nullable = false)
    private int capacidade;

    @ElementCollection
    @CollectionTable(name = "estacao_infraestruturas", joinColumns = @JoinColumn(name = "estacao_id"))
    @Column(name = "infraestrutura")
    private List<String> infraestruturas;
    
    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;
    
    @Column(name = "tempo_medio_espera")
    private double tempoMedioEspera;
    
    @Column(nullable = false)
    private boolean atrasos;
    
    @Min(value = 0, message = "A ocupação não pode ser negativa")
    @Max(value = 100, message = "A ocupação não pode exceder 100%")
    @Column(nullable = false)
    private double ocupacao;
    
    @Column(name = "sensores_ambientais", length = 1000)
    private String sensoresAmbientais;
    
    @Column(name = "eventos_especiais", length = 1000)
    private String eventosEspeciais;
    
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    
    @Version
    private Long versao;
    
    @Column(name = "status_operacional")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusOperacional statusOperacional = StatusOperacional.NORMAL;
    
    public enum StatusOperacional {
        NORMAL, MANUTENÇÃO, FECHADA, EMERGÊNCIA
    }
}