package com.example.cloudwrite.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ExpositionPiece implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    // for topic search purposes
    String keyword;

    @OneToOne
    Standfirst standfirst;

    String expositionPurpose;

    String currentProgress;

    @OneToMany(mappedBy = "expositionPiece")
    List<KeyResult> keyResults;

    String futureWork;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;
}
