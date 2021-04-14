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
public class ResearchPiece implements Serializable, Comparable<ResearchPiece> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // for topic search purposes
    private String keyword;

    @OneToOne
    private Standfirst standfirst;

    private String researchPurpose;

    private String currentProgress;

    @OneToMany(mappedBy = "piece")
    private List<Citation> citations;

    @OneToMany(mappedBy = "researchPiece")
    private List<KeyResult> keyResults;

    private String futureWork;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    // restrict pieces by unique title
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj instanceof ResearchPiece){
            ResearchPiece passed = (ResearchPiece) obj;
            String passedTitle = passed.title;

            String thisTitle = this.title;
            return (thisTitle.equals(passedTitle));
        }
        return false;
    }

    // list research pieces by title
    @Override
    public int compareTo(ResearchPiece o) {
        String thisPiece = this.title;
        String inputTitle = o.title;
        return thisPiece.compareTo(inputTitle);
    }
}
