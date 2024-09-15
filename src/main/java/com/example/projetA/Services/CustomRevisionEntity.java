package com.example.projetA.Services;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "REVINFO")
public class CustomRevisionEntity extends DefaultRevisionEntity {

    //@Enumerated(EnumType.STRING)
    @Column(name = "revision_type")
    private String revisionType;

    private String entityName;


    @Column(name = "name")
    private String name;

    /*public RevisionType getRevisionType() {
        return revisionType == null ? null : RevisionType.values()[revisionType];
    }

    public void setRevisionType(RevisionType revisionType) {
        this.revisionType = revisionType == null ? null : (byte) revisionType.ordinal();
    }*/
}
