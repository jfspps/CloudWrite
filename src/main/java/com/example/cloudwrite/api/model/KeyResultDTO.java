package com.example.cloudwrite.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KeyResultDTO {

    private Long id;

    private String description;

    private Integer priority;
}
