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
 *        Time: 13:10:41
 */

public class NameComparator implements Comparator<PersonenName>, Serializable {

    @Override
    public int compare(final PersonenName n1, final PersonenName n2) {
        final Integer order1 = n1.getReihenfolge();
        final Integer order2 = n2.getReihenfolge();
        return order1.compareTo(order2);
    }
}
