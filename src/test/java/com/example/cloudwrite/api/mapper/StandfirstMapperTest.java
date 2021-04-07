package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.StandfirstDTO;
import com.example.cloudwrite.model.Standfirst;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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