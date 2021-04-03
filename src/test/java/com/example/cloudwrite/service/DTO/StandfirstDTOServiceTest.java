package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.StandfirstRepo;
import com.example.cloudwrite.api.mapper.StandfirstMapper;
import com.example.cloudwrite.api.model.StandfirstDTO;
import com.example.cloudwrite.model.Standfirst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandfirstDTOServiceTest {

    private StandfirstDTOService standfirstDTOService;

    @Mock
    private StandfirstRepo standfirstRepo;

    @BeforeEach
    void setUp() {
        standfirstDTOService = new StandfirstDTOServiceImpl(StandfirstMapper.INSTANCE, standfirstRepo);
    }

    @Test
    void findById() {
        when(standfirstRepo.findById(anyLong())).thenReturn(Optional.of(Standfirst.builder().approach("an approach").build()));

        // call the DTO service interface (when) as defined by its implementation class
        StandfirstDTO found = standfirstDTOService.findById(3L);

        // check mapping (then)
        assertEquals("an approach", found.getApproach());
    }

    @Test
    void findAll() {
        // define what is returned through JPA repo (given)
        List<Standfirst> standfirstList = Arrays.asList(new Standfirst(), new Standfirst());
        when(standfirstRepo.findAll()).thenReturn(standfirstList);

        // call the DTO service interface (when) as defined by its implementation class
        List<StandfirstDTO> standfirstDTOList = standfirstDTOService.findAll();

        // check mapping (then)
        assertEquals(2, standfirstDTOList.size());
    }
}