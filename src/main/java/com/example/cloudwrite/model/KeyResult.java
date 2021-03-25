package com.example.cloudwrite.model;

import lombok.*;

import javax.persistence.*;

/**
 * Stores a key research result and paired to an exposition piece
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KeyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String description;

    @ManyToOne
    ExpositionPiece expositionPiece;
}
