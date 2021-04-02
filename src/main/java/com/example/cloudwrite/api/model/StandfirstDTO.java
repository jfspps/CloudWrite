package com.example.cloudwrite.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StandfirstDTO {

    private Long id;

    private String rationale;

    private String approach;
}
