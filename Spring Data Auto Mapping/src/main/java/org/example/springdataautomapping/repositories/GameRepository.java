package org.example.springdataautomapping.repositories;

import org.example.springdataautomapping.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {


    List<Game> id(Long id);
}
