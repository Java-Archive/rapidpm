package org.rapidpm.persistence.prj.textelement;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class TextElementDAO extends DAO<Long, TextElement> {
    private static final Logger logger = Logger.getLogger(TextElementDAO.class);


    public TextElementDAO(final EntityManager entityManager) {
        super(entityManager, TextElement.class);
    }
}