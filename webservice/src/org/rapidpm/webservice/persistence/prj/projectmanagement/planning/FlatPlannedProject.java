package org.rapidpm.webservice.persistence.prj.projectmanagement.planning;

import org.rapidpm.webservice.mapping.FlatEntity;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 14:00
 */
public class FlatPlannedProject extends FlatEntity {
    // TODO
    private String projektName;

    public String getProjektName() {
        return projektName;
    }

    public void setProjektName(final String projektName) {
        this.projektName = projektName;
    }
}
