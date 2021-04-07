package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.CitationDTO;
import com.example.cloudwrite.JPARepository.CitationRepo;
import com.example.cloudwrite.api.mapper.CitationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitationDTOServiceImpl implements CitationDTOService{

    private final CitationMapper citationMapper;
    private final CitationRepo citationRepo;

    public CitationDTOServiceImpl(CitationMapper citationMapper, CitationRepo citationRepo) {
        this.citationMapper = citationMapper;
        this.citationRepo = citationRepo;
    }

    @Override
    public List<CitationDTO> findAll() {
        return citationRepo.findAll()
                .stream()
                .map(citationMapper::citationToCitationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CitationDTO findById(Long id) {
        return citationRepo.findById(id)
                .map(citationMapper::citationToCitationDTO)
                .orElse(null);
    }
}
