package com.example.projetA.Services;

import jakarta.persistence.EntityManager;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomRevisionListener implements RevisionListener {

    private static EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        CustomRevisionListener.entityManager = entityManager;
    }
    @Override
    public void newRevision(Object revisionEntity) {
        if (!(revisionEntity instanceof CustomRevisionEntity)) {
            return;
        }
        CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            customRevisionEntity.setName(authentication.getName());
        }


        //customRevisionEntity.setEntityName(Appareils.class.getSimpleName());

        RevisionType revisionType = RevisionTypeHolder.getRevisionType();
        String revisionTypeString = convertRevisionTypeToString(revisionType);

        customRevisionEntity.setRevisionType(revisionTypeString);
        customRevisionEntity.setEntityName(RevisionTypeHolder.getEntityName());
    }

    private String convertRevisionTypeToString(RevisionType revisionType) {
        if (revisionType == null) {
            return "Inconnu";
        }
        switch (revisionType) {
            case ADD:
                return "Ajout";
            case MOD:
                return "Modification";
            case DEL:
                return "Suppression";
            default:
                return "Inconnu";
        }
    }

        /*RevisionType revisionType = RevisionTypeHolder.getRevisionType();
        if (revisionType != null) {
            customRevisionEntity.setRevisionType(revisionType);
        }*/

        /*RevisionType revisionType = AuditReaderFactory.get(entityManager)
                .getCurrentRevision(RevisionType.class, Appareils.class.getName());

        customRevisionEntity.setRevisionType(revisionType);
        customRevisionEntity.setEntityName(Appareils.class.getSimpleName());*/


}
