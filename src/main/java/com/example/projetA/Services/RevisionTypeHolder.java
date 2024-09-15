package com.example.projetA.Services;

import org.hibernate.envers.RevisionType;

public class RevisionTypeHolder {
    private static final ThreadLocal<RevisionType> REVISION_TYPE = new ThreadLocal<>();
    private static final ThreadLocal<String> ENTITY_NAME = new ThreadLocal<>();

    public static void setRevisionType(RevisionType type) {
        REVISION_TYPE.set(type);
    }

    public static RevisionType getRevisionType() {
        RevisionType type = REVISION_TYPE.get();
        REVISION_TYPE.remove();
        return type;
    }

    public static void setEntityName(String name) {
        ENTITY_NAME.set(name);
    }

    public static String getEntityName() {
        String name = ENTITY_NAME.get();
        ENTITY_NAME.remove();
        return name;
    }

    public static void clear() {
        REVISION_TYPE.remove();
        ENTITY_NAME.remove();
    }
}