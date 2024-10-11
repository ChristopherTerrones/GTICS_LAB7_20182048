package com.example.gtics_lab7_20182048.Repository;

import com.example.gtics_lab7_20182048.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Room, Integer> {
}
