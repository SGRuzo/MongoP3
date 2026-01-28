package org.example.service;

import org.example.model.Actor;
import org.example.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Optional<Actor> findById(Integer id) {
        return actorRepository.findById(id);
    }

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public boolean existsById(Integer id) {
        return actorRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        actorRepository.deleteById(id);
    }
}
