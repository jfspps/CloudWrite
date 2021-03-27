package com.example.cloudwrite.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Stores a purpose and description of a concept, and is paired to a fundamental piece
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Concept implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String purpose;

    String description;

    @ManyToOne
    FundamentalPiece fundamentalPiece;
}
