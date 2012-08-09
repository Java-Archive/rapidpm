package org.rapidpm.orm.security;

import org.rapidpm.orm.EntityFactory;
import org.rapidpm.orm.system.security.NewPasswdRequest;
import org.rapidpm.util.RandomNameFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:48
 */
public class NewPasswdRequestEntityFactory extends EntityFactory<NewPasswdRequest> {

    public NewPasswdRequestEntityFactory() {
        super(NewPasswdRequest.class);
    }

    @Override
    public NewPasswdRequest createRandomEntity() {
        final RandomNameFactory nameFactory = RandomNameFactory.getInstance();

        final NewPasswdRequest newPasswdRequest = new NewPasswdRequest();
        newPasswdRequest.setVorname(nameFactory.getRandomVorname());
        newPasswdRequest.setNachname(nameFactory.getRandomNachname());
        newPasswdRequest.setEmail((newPasswdRequest.getVorname() + '.' + newPasswdRequest.getNachname()).toLowerCase() + "@mail.de");
        newPasswdRequest.setMandantengruppe(new MandantengruppeEntityFactory().createRandomEntity());
        return newPasswdRequest;
    }
}