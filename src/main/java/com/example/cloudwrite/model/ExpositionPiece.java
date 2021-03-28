package com.example.cloudwrite.model;

import com.example.cloudwrite.model.security.User;
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
public class ExpositionPiece implements Serializable, Comparable<ExpositionPiece> {

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

    @OneToMany(mappedBy = "piece")
    List<Citation> citations;

    @OneToMany(mappedBy = "expositionPiece")
    List<KeyResult> keyResults;

    String futureWork;

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

        if (obj instanceof ExpositionPiece){
            ExpositionPiece passed = (ExpositionPiece) obj;
            String passedTitle = passed.title;

            String thisTitle = this.title;
            return (thisTitle.equals(passedTitle));
        }
        return false;
    }

    // list exposition pieces by title
    @Override
    public int compareTo(ExpositionPiece o) {
        String thisPiece = this.title;
        String inputTitle = o.title;
        return thisPiece.compareTo(inputTitle);
    }
}
