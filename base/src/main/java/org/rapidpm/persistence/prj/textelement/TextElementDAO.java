package org.rapidpm.persistence.prj.textelement;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class TextElementDAO extends DAO<Long, TextElement> {
    private static final Logger logger = Logger.getLogger(TextElementDAO.class);


    public TextElementDAO(final OrientGraph orientDB) {
        super(orientDB, TextElement.class);
    }

    @Override
    public TextElement loadFull(TextElement entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}