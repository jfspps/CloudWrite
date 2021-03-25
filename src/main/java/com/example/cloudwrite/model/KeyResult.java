package com.example.cloudwrite.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Stores a key research result and paired to an exposition piece
 */
@Builder
@NoArgsConstructor
@Data
@Entity
public class KeyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String description;

    @ManyToOne
    ExpositionPiece expositionPiece;
}
