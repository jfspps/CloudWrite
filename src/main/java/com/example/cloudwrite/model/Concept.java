package com.example.cloudwrite.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Stores a purpose and description of a concept, and is paired to a fundamental piece
 */
@Builder
@NoArgsConstructor
@Data
@Entity
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String purpose;

    String description;

    @ManyToOne
    FundamentalPiece fundamentalPiece;
}
