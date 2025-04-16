package com.DataBase.DigitalTwins.Backend.Classes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PontoVia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double latitude;
    private double longitude;
    private int sequencia;
    
    @ManyToOne
    @JoinColumn(name = "via_id")
    private Via via;
}