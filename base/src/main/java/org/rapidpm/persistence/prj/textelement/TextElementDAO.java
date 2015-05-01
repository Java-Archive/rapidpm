package org.rapidpm.persistence.prj.textelement;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class TextElementDAO extends DAO<Long, TextElement> {
    private static final Logger logger = Logger.getLogger(TextElementDAO.class);


    public TextElementDAO(final OrientGraph orientDB) {
        super(orientDB, TextElement.class);
    }
}