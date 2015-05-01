package org.rapidpm.persistence.prj.textelement.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class TextElementKommentarDAO extends DAO<Long, TextElementKommentar> {
    private static final Logger logger = Logger.getLogger(TextElementKommentarDAO.class);


    public TextElementKommentarDAO(final OrientGraph orientDB) {
        super(orientDB, TextElementKommentar.class);
    }
}
