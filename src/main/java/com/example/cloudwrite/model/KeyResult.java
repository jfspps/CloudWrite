package com.example.cloudwrite.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Stores a key research result and paired to an exposition piece
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KeyResult implements Serializable, Comparable<KeyResult> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String description = "";

    @ManyToOne
    private ExpositionPiece expositionPiece;

    // used to sort key results in the order they should appear in the article
    @Builder.Default
    private Integer priority = 0;

    @Override
    public int compareTo(KeyResult o) {
        Integer thisResult = this.priority;
        Integer inputResult = o.priority;
        return thisResult.compareTo(inputResult);
    }

    // used to delete a result from a list of results
    @Builder.Default
    private boolean deletable = false;
}
