package com.DataBase.DigitalTwins.Backend.Classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Viaturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Viatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A matrícula é obrigatória")
    @Column(nullable = false, unique = true, length = 50)
    private String matricula;

    @Min(1990) @Max(2026)
    @Column(name = "ano_fabricacao", nullable = false)
    private int anoFabricacao;

    @NotBlank
    @Column(name = "tipo_servico", nullable = false, length = 50)
    private String tipoServico;

    @Column(columnDefinition = "DECIMAL(9,6)")
    private Double latitude;

    @Column(columnDefinition = "DECIMAL(9,6)")
    private Double longitude;

    @DecimalMin("0") @DecimalMax("100")
    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double velocidade;

    @Min(0) @Max(120)
    private Integer ocupacao;

    @DecimalMin("0") @DecimalMax("100")
    @Column(name = "nivel_energia", columnDefinition = "DECIMAL(5,2)")
    private Double nivelEnergia;

    @Convert(converter = StatusOperacionalConverter.class)
    @Column(name = "status_operacional", nullable = false, length = 20)
    private StatusOperacional statusOperacional;

    public enum StatusOperacional {
        EM_SERVICO("Em serviço"),
        FORA_DE_SERVICO("Fora de serviço"),
        MANUTENCAO("Manutenção"),
        AVARIADO("Avariado");

        private final String dbValue;

        StatusOperacional(String dbValue) {
            this.dbValue = dbValue;
        }

        public String getDbValue() {
            return dbValue;
        }

        public static StatusOperacional fromDbValue(String dbValue) {
            for (StatusOperacional status : values()) {
                if (status.dbValue.equals(dbValue)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Status desconhecido: " + dbValue);
        }
    }

    @Converter(autoApply = true)
    public static class StatusOperacionalConverter implements AttributeConverter<StatusOperacional, String> {
        @Override
        public String convertToDatabaseColumn(StatusOperacional attribute) {
            return attribute == null ? null : attribute.getDbValue();
        }

        @Override
        public StatusOperacional convertToEntityAttribute(String dbData) {
            return dbData == null ? null : StatusOperacional.fromDbValue(dbData);
        }
    }
}