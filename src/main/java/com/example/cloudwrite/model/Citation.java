package com.example.cloudwrite.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Stores a citation or reference
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Citation implements Serializable, Comparable<Citation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String reference = "";

    @ManyToOne
    private ExpositionPiece piece;

    @Override
    public int compareTo(Citation o) {
        String thisRef = this.reference;
        String inputRef = o.reference;
        return thisRef.compareTo(inputRef);
    }

    @Builder.Default
    private boolean deletable = false;
}
