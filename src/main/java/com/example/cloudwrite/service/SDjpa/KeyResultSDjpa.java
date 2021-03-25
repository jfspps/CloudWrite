package com.example.cloudwrite.service.SDjpa;

import com.example.cloudwrite.JPARepository.KeyResultRepo;
import com.example.cloudwrite.model.KeyResult;
import com.example.cloudwrite.service.KeyResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class KeyResultSDjpa implements KeyResultService {

    private final KeyResultRepo keyResultRepo;

    public KeyResultSDjpa(KeyResultRepo keyResultRepo) {
        this.keyResultRepo = keyResultRepo;
    }

    @Override
    public KeyResult save(KeyResult object) {
        return keyResultRepo.save(object);
    }

    @Override
    public KeyResult findById(Long aLong) {
        return keyResultRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<KeyResult> findAll() {
        Set<KeyResult> keyResults = new HashSet<>();
        keyResults.addAll(keyResultRepo.findAll());
        return keyResults;
    }

    @Override
    public void delete(KeyResult objectT) {
        keyResultRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        keyResultRepo.deleteById(aLong);
    }
}
