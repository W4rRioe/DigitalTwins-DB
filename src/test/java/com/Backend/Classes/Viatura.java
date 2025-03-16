package com.Backend.Classes;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Viaturas") // Nome da tabela igual ao banco de dados
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Viatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A matrícula é obrigatória")
    @Column(nullable = false, unique = true)
    private String matricula;

    @Min(value = 1990, message = "Ano de fabricação deve estar entre 1990 e 2026")
    @Max(value = 2026, message = "Ano de fabricação deve estar entre 1990 e 2026")
    @Column(name = "ano_fabricacao", nullable = false)
    private int anoFabricacao;

    @NotBlank(message = "O tipo de serviço é obrigatório")
    @Column(name = "tipo_servico", nullable = false)
    private String tipoServico;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "velocidade")
    @Min(value = 0, message = "A velocidade não pode ser negativa")
    @Max(value = 100, message = "A velocidade máxima permitida é 100 km/h")
    private Double velocidade;

    @Column(name = "ocupacao")
    @Min(value = 0, message = "A ocupação não pode ser negativa")
    @Max(value = 120, message = "A ocupação não pode exceder 120 passageiros")
    private Integer ocupacao;

    @Column(name = "nivel_energia")
    @Min(value = 0, message = "O nível de energia não pode ser negativo")
    @Max(value = 100, message = "O nível de energia deve estar entre 0 e 100%")
    private Double nivelEnergia;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_operacional", nullable = false)
    private StatusOperacional statusOperacional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "via_atual_id")
    private Via viaAtual; // Relacionamento com a tabela Vias

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacao_atual_id")
    private Estacao estacaoAtual; // Relacionamento com a tabela Estacoes

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public enum StatusOperacional {
        EM_SERVICO, FORA_DE_SERVICO, MANUTENCAO, AVARIADO
    }

    public Long getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getVelocidade() {
        return velocidade;
    }

    public Integer getOcupacao() {
        return ocupacao;
    }

    public Double getNivelEnergia() {
        return nivelEnergia;
    }

    public StatusOperacional getStatusOperacional() {
        return statusOperacional;
    }

    public Via getViaAtual() {
        return viaAtual;
    }

    public Estacao getEstacaoAtual() {
        return estacaoAtual;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}