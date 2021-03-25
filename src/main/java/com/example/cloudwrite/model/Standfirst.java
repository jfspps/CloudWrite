package com.example.cloudwrite.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
