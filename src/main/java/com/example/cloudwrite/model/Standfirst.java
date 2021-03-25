package com.example.cloudwrite.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@Data
@Entity
public class Standfirst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String rationale;

    String approach;

    @OneToOne
    ExpositionPiece expositionPiece;
}
