package org.rapidpm.persistence.prj.textelement;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class TextElementDAO extends DAO<Long, TextElement> {


    public TextElementDAO(final EntityManager entityManager) {
        super(entityManager, TextElement.class);
    }
}