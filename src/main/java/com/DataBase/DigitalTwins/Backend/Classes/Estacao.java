package com.DataBase.DigitalTwins.Backend.Classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Estacoes")
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
    @Column(nullable = false, unique = true)
    private String nome;

    @NotNull(message = "A latitude é obrigatória")
    @Column(columnDefinition = "DECIMAL(9,6)")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória")
    @Column(columnDefinition = "DECIMAL(9,6)")
    private Double longitude;

    @NotNull(message = "A capacidade é obrigatória")
    @Min(value = 50, message = "A capacidade mínima é 50")
    @Max(value = 500, message = "A capacidade máxima é 500")
    @Column(nullable = false)
    private Integer capacidade;

    @NotNull(message = "O tempo médio de espera é obrigatório")
    @Min(value = 5, message = "O tempo mínimo de espera é 5 minutos")
    @Max(value = 60, message = "O tempo máximo de espera é 60 minutos")
    @Column(name = "tempo_medio_espera", nullable = false)
    private Integer tempoMedioEspera;

    @NotNull(message = "A ocupação é obrigatória")
    @Min(value = 0, message = "A ocupação não pode ser negativa")
    @Max(value = 500, message = "A ocupação máxima é 500 passageiros")
    @Column(nullable = false)
    private Integer ocupacao;

    private Double sensorTemperatura;

    @Version
    @Column(nullable = false)
    private Long versao;

    // Método para atualizar as coordenadas
    public void atualizarCoordenadas(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude e longitude não podem ser nulas");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }
}