package org.rapidpm.persistence.security;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:46
 */
public class BenutzerWebapplikationEntityFactory extends EntityFactory<BenutzerWebapplikation> {

    public BenutzerWebapplikationEntityFactory() {
        super(BenutzerWebapplikation.class);
    }

    @Override
    public BenutzerWebapplikation createRandomEntity() {
        final BenutzerWebapplikation benutzerWebapplikation = new BenutzerWebapplikation();
        benutzerWebapplikation.setWebappName(RND.nextLetterString(8, 16));
        return benutzerWebapplikation;
    }
}