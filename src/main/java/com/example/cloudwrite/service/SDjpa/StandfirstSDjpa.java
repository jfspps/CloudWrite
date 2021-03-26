package com.example.cloudwrite.service.SDjpa;

import com.example.cloudwrite.JPARepository.StandfirstRepo;
import com.example.cloudwrite.model.Standfirst;
import com.example.cloudwrite.service.StandfirstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("SDjpa")
public class StandfirstSDjpa implements StandfirstService {

    private final StandfirstRepo standfirstRepo;

    public StandfirstSDjpa(StandfirstRepo standfirstRepo) {
        this.standfirstRepo = standfirstRepo;
    }

    @Override
    public Standfirst save(Standfirst object) {
        return standfirstRepo.save(object);
    }

    @Override
    public Standfirst findById(Long aLong) {
        return standfirstRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<Standfirst> findAll() {
        Set<Standfirst> standfirsts = new HashSet<>();
        standfirsts.addAll(standfirstRepo.findAll());
        return standfirsts;
    }

    @Override
    public void delete(Standfirst objectT) {
        standfirstRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        standfirstRepo.deleteById(aLong);
    }
}
