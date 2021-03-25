package com.example.cloudwrite.service.SDjpa;

import com.example.cloudwrite.JPARepository.ExpositionPieceRepo;
import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.service.ExpositionPieceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ExpositionSDjpa implements ExpositionPieceService {

    private final ExpositionPieceRepo expositionPieceRepo;

    public ExpositionSDjpa(ExpositionPieceRepo expositionPieceRepo) {
        this.expositionPieceRepo = expositionPieceRepo;
    }

    @Override
    public ExpositionPiece save(ExpositionPiece object) {
        return expositionPieceRepo.save(object);
    }

    @Override
    public ExpositionPiece findById(Long aLong) {
        return expositionPieceRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<ExpositionPiece> findAll() {
        Set<ExpositionPiece> found = new HashSet<>();
        found.addAll(expositionPieceRepo.findAll());
        return found;
    }

    @Override
    public void delete(ExpositionPiece objectT) {
        expositionPieceRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        expositionPieceRepo.deleteById(aLong);
    }

    @Override
    public Set<ExpositionPiece> findByKeywords(List<String> keywords) {
        return expositionPieceRepo.findAllByKeywords(keywords);
    }

    @Override
    public Set<ExpositionPiece> findByTitle(String title) {
        return expositionPieceRepo.findAllByTitle(title);
    }
}
