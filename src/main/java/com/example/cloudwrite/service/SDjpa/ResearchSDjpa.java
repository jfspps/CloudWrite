package com.example.cloudwrite.service.SDjpa;

import com.example.cloudwrite.JPARepository.ResearchPieceRepo;
import com.example.cloudwrite.model.ResearchPiece;
import com.example.cloudwrite.service.ResearchPieceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Profile("SDjpa")
public class ResearchSDjpa implements ResearchPieceService {

    private final ResearchPieceRepo researchPieceRepo;

    public ResearchSDjpa(ResearchPieceRepo researchPieceRepo) {
        this.researchPieceRepo = researchPieceRepo;
    }

    @Override
    public ResearchPiece save(ResearchPiece object) {
        return researchPieceRepo.save(object);
    }

    @Override
    public ResearchPiece findById(Long aLong) {
        return researchPieceRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<ResearchPiece> findAll() {
        Set<ResearchPiece> found = new HashSet<>();
        found.addAll(researchPieceRepo.findAll());
        return found;
    }

    @Override
    public void delete(ResearchPiece objectT) {
        researchPieceRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        researchPieceRepo.deleteById(aLong);
    }

    @Override
    public List<ResearchPiece> findAllByKeyword(String keyword) {
        return researchPieceRepo.findAllByKeywordContainingIgnoreCase(keyword);
    }

    @Override
    public List<ResearchPiece> findAllByTitle(String title) {
        return researchPieceRepo.findAllByTitle(title);
    }
}
