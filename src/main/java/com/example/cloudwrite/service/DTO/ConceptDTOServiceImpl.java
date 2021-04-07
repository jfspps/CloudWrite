package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.ConceptDTO;
import com.example.cloudwrite.JPARepository.ConceptRepo;
import com.example.cloudwrite.api.mapper.ConceptMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConceptDTOServiceImpl implements ConceptDTOService{

    private final ConceptMapper conceptMapper;
    private final ConceptRepo conceptRepo;

    public ConceptDTOServiceImpl(ConceptMapper conceptMapper, ConceptRepo conceptRepo) {
        this.conceptMapper = conceptMapper;
        this.conceptRepo = conceptRepo;
    }

    @Override
    public List<ConceptDTO> findAll() {
        return conceptRepo
                .findAll()
                .stream()
                .map(conceptMapper::conceptToConceptDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConceptDTO findById(Long id) {
        return conceptRepo
                .findById(id)
                .map(conceptMapper::conceptToConceptDTO)
                .orElse(null);
    }
}
