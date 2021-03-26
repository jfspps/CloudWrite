package com.example.cloudwrite.service.SDjpa;

import com.example.cloudwrite.JPARepository.FundamentalPieceRepo;
import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.FundamentalPiece;
import com.example.cloudwrite.service.FundamentalPieceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("SDjpa")
public class FundamentalSDjpa implements FundamentalPieceService {

    private final FundamentalPieceRepo fundamentalPieceRepo;

    public FundamentalSDjpa(FundamentalPieceRepo fundamentalPieceRepo) {
        this.fundamentalPieceRepo = fundamentalPieceRepo;
    }

    @Override
    public FundamentalPiece save(FundamentalPiece object) {
        return fundamentalPieceRepo.save(object);
    }

    @Override
    public FundamentalPiece findById(Long aLong) {
        return fundamentalPieceRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<FundamentalPiece> findAll() {
        Set<FundamentalPiece> found = new HashSet<>();
        found.addAll(fundamentalPieceRepo.findAll());
        return found;
    }

    @Override
    public void delete(FundamentalPiece objectT) {
        fundamentalPieceRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        fundamentalPieceRepo.deleteById(aLong);
    }

    @Override
    public Set<ExpositionPiece> findAllByKeyword(String keyword) {
        return fundamentalPieceRepo.findAllByKeyword(keyword);
    }

    @Override
    public Set<ExpositionPiece> findAllByTitle(String title) {
        return fundamentalPieceRepo.findAllByTitle(title);
    }
}
