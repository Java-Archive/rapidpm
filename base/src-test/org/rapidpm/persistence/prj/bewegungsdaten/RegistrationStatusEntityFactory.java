package org.rapidpm.persistence.prj.bewegungsdaten;

import org.rapidpm.persistence.DataSetEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:50
 */
public class RegistrationStatusEntityFactory extends DataSetEntityFactory<RegistrationStatus, String> {

    public RegistrationStatusEntityFactory() {
        super(RegistrationStatus.class, "Zur_Prüfung", "Abgelehnt", "Akzeptiert", "freigeschaltet");
    }

    @Override
    public RegistrationStatus createRandomEntity() {
        final RegistrationStatus registrationStatus = new RegistrationStatus();
        registrationStatus.setStatus(combineRandomStringWithNextIndex(dataSet));
        return registrationStatus;
    }
}
