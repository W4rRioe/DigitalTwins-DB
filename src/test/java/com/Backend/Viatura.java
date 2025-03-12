package com.Backend;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "viaturas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Viatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A matrícula é obrigatória")
    @Pattern(regexp = "^[A-Z0-9]{2,8}$", message = "Formato de matrícula inválido")
    @Column(nullable = false, unique = true)
    private String matricula;

    @Min(value = 1900, message = "Ano de fabricação inválido")
    @Column(name = "ano_fabricacao", nullable = false)
    private int anoFabricacao;

    @NotBlank(message = "O tipo de serviço é obrigatório")
    @Column(name = "tipo_servico", nullable = false)
    private String tipoServico;
    
    @Column(name = "status_operacional")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusOperacional statusOperacional = StatusOperacional.ATIVO;
    
    @Column(name = "distancia_proxima_paragem")
    private double distanciaProximaParagem;
    
    @Column(name = "consumo_medio")
    private double consumoMedio;
    
    @Min(value = 0, message = "A velocidade não pode ser negativa")
    @Column(name = "velocidade_instantanea")
    private double velocidadeInstantanea;
    
    @Min(value = 0, message = "A ocupação não pode ser negativa")
    @Max(value = 100, message = "A ocupação não pode exceder 100%")
    private double ocupacao;
    
    @Column(name = "temperatura_interna")
    private double temperaturaInterna;
    
    @Column(name = "temperatura_externa")
    private double temperaturaExterna;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "via_atual_id")
    private Via viaAtual;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacao_atual_id")
    private Estacao estacaoAtual;
    
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
    
    @Column(name = "nivel_combustivel")
    @Min(value = 0, message = "O nível de combustível não pode ser negativo")
    @Max(value = 100, message = "O nível de combustível não pode exceder 100%")
    private double nivelCombustivel;
    
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    
    @Version
    private Long versao;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_combustivel")
    private TipoCombustivel tipoCombustivel;
    
    public enum StatusOperacional {
        ATIVO, MANUTENÇÃO, FORA_DE_SERVIÇO, EM_ROTA, PARADO
    }
    
    public enum TipoCombustivel {
        DIESEL, GASOLINA, ELÉTRICO, HÍBRIDO, GÁS_NATURAL
    }
}