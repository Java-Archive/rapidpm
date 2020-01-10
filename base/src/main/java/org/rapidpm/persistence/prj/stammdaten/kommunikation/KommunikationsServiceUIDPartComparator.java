/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence.prj.stammdaten.kommunikation;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 12.01.11
 * Time: 09:19
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */


import java.io.Serializable;
import java.util.Comparator;

public class KommunikationsServiceUIDPartComparator implements Comparator<KommunikationsServiceUIDPart>, Serializable {

    @Override
    public int compare(final KommunikationsServiceUIDPart o1, final KommunikationsServiceUIDPart o2) {
        return ((Integer) o1.getOrderNr()).compareTo(o2.getOrderNr());
    }
}
