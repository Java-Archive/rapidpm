package org.rapidpm.persistence.prj.stammdaten.person;


import java.io.Serializable;
import java.util.Comparator;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 07.10.2010
 *        Time: 15:45:45
 */

public class TitelComparator implements Comparator<Titel>, Serializable {

    @Override
    public int compare(final Titel o1, final Titel o2) {
        final Integer i1 = o1.getTitelNr();
        final Integer i2 = o2.getTitelNr();
        return i1.compareTo(i2);
    }
}
