package com.example.cloudwrite.service.SDjpa;

import com.example.cloudwrite.JPARepository.CitationRepo;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.service.CitationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("SDjpa")
public class CitationSDjpa implements CitationService {

    private final CitationRepo citationRepo;

    public CitationSDjpa(CitationRepo citationRepo) {
        this.citationRepo = citationRepo;
    }

    @Override
    public Citation save(Citation object) {
        return citationRepo.save(object);
    }

    @Override
    public Citation findById(Long aLong) {
        return citationRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<Citation> findAll() {
        return new HashSet<>(citationRepo.findAll());
    }

    @Override
    public void delete(Citation objectT) {
        citationRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        citationRepo.deleteById(aLong);
    }
}
