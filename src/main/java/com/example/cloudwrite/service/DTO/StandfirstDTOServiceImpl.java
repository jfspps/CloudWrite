package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.StandfirstDTO;
import com.example.cloudwrite.JPARepository.StandfirstRepo;
import com.example.cloudwrite.api.mapper.StandfirstMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StandfirstDTOServiceImpl implements StandfirstDTOService{

    private final StandfirstMapper standfirstMapper;
    private final StandfirstRepo standfirstRepo;

    public StandfirstDTOServiceImpl(StandfirstMapper standfirstMapper, StandfirstRepo standfirstRepo) {
        this.standfirstMapper = standfirstMapper;
        this.standfirstRepo = standfirstRepo;
    }

    @Override
    public List<StandfirstDTO> findAll() {
        return standfirstRepo
                .findAll()
                .stream()
                .map(standfirstMapper::standfirstToStandfirstDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StandfirstDTO findById(Long id) {
        return standfirstRepo.findById(id)
                .map(standfirstMapper::standfirstToStandfirstDTO)
                .orElse(null);
    }
}
