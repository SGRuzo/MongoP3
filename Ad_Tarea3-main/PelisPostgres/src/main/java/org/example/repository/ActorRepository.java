package org.example.repository;

import org.example.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository <Actor, Long> {

    List<Actor> findByNome(String nome);
}
