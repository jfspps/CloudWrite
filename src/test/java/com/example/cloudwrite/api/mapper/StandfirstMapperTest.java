package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.StandfirstDTO;
import com.example.cloudwrite.model.Standfirst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandfirstMapperTest {

    private static final String RATIONALE = "The standfirst rationale";
    private static final String APPROACH = "The standfirst approach";

    @Test
    void standfirstToStandfirstDTO() {
        Standfirst standfirst = Standfirst.builder().rationale(RATIONALE).approach(APPROACH).build();

        StandfirstDTO standfirstDTO = StandfirstMapper.INSTANCE.standfirstToStandfirstDTO(standfirst);

        assertEquals(RATIONALE, standfirstDTO.getRationale());
        assertEquals(APPROACH, standfirstDTO.getApproach());
    }
}