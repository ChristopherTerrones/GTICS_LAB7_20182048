package com.example.gtics_lab7_20182048.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "funcionId")
    private Funcion funcion;

    @ManyToOne
    @JoinColumn(name = "roomSeatId")
    private RoomSeat roomSeat;

    @Column(name = "startDatetime", nullable = false)
    private Date startDatetime;

    @Column(name = "endDatetime", nullable = false)
    private Date endDatetime;
}

