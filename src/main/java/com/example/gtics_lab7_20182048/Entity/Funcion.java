package com.example.gtics_lab7_20182048.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "funciones")
public class Funcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "obraId")
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @Column(name = "funcionDate", nullable = false)
    private Date funcionDate;

    @Column(name = "availableSeats")
    private Integer availableSeats;
}

