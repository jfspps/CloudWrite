package com.example.cloudwrite.service.SDjpa;

import com.example.cloudwrite.JPARepository.ConceptRepo;
import com.example.cloudwrite.model.Concept;
import com.example.cloudwrite.service.ConceptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("SDjpa")
public class ConceptSDjpa implements ConceptService {

    private final ConceptRepo conceptRepo;

    public ConceptSDjpa(ConceptRepo conceptRepo) {
        this.conceptRepo = conceptRepo;
    }

    @Override
    public Concept save(Concept object) {
        return conceptRepo.save(object);
    }

    @Override
    public Concept findById(Long aLong) {
        return conceptRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<Concept> findAll() {
        Set<Concept> concepts = new HashSet<>();
        concepts.addAll(conceptRepo.findAll());
        return concepts;
    }

    @Override
    public void delete(Concept objectT) {
        conceptRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        conceptRepo.deleteById(aLong);
    }
}
