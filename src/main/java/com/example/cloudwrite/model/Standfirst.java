package com.example.cloudwrite.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Standfirst implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String rationale;

    String approach;

    @OneToOne
    ExpositionPiece expositionPiece;
}
