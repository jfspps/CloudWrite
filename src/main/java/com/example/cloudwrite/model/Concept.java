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
public class Concept implements Serializable, Comparable<Concept> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String purpose;

    String description;

    @ManyToOne
    FundamentalPiece fundamentalPiece;

    // used to sort key results in the order they should appear in the article
    @Builder.Default
    Integer priority = 0;

    @Override
    public int compareTo(Concept o) {
        Integer thisResult = this.priority;
        Integer inputResult = o.priority;
        return thisResult.compareTo(inputResult);
    }

    // used to delete a result from a list of results
    @Builder.Default
    boolean deletable = false;
}
