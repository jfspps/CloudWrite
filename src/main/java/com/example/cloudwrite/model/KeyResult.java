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
    Long id;

    @Builder.Default
    String description = "";

    @ManyToOne
    ExpositionPiece expositionPiece;

    // used to sort key results in the order they should appear in the article
    @Builder.Default
    Integer priority = 0;

    @Override
    public int compareTo(KeyResult o) {
        Integer thisResult = this.priority;
        Integer inputResult = o.priority;
        return thisResult.compareTo(inputResult);
    }
}
