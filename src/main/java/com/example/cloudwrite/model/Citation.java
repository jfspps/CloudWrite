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
    Long id;

    @Builder.Default
    String ref = "";

    @ManyToOne
    ExpositionPiece piece;

    @Override
    public int compareTo(Citation o) {
        String thisRef = this.ref;
        String inputRef = o.ref;
        return thisRef.compareTo(inputRef);
    }

    @Builder.Default
    boolean deletable = false;
}
