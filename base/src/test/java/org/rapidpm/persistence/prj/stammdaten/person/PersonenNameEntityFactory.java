package org.rapidpm.persistence.prj.stammdaten.person;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.util.RandomNameFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.10.11
 * Time: 13:40
 */
public class PersonenNameEntityFactory extends EntityFactory<PersonenName> {
    public PersonenNameEntityFactory() {
        super(PersonenName.class);
    }

    @Override
    public PersonenName createRandomEntity() {
        final RandomNameFactory nameFactory = RandomNameFactory.getInstance();
        final PersonenName personenName = new PersonenName();
        personenName.setName(nameFactory.getRandomName());
        personenName.setNamensKlassifizierung(new NamensKlassifizierungEntityFactory().createRandomEntity());
        personenName.setReihenfolge(RND.nextInt(1, 9));
        return personenName;
    }

    public PersonenName createRandomVorname() {
        final RandomNameFactory nameFactory = RandomNameFactory.getInstance();
        final PersonenName personenName = new PersonenName();
        personenName.setName(nameFactory.getRandomVorname());
        personenName.setNamensKlassifizierung(new NamensKlassifizierungEntityFactory().createVornameKlassifizierung());
        personenName.setReihenfolge(1);
        return personenName;
    }

    public PersonenName createRandomNachname() {
        final RandomNameFactory nameFactory = RandomNameFactory.getInstance();
        final PersonenName personenName = new PersonenName();
        personenName.setName(nameFactory.getRandomNachname());
        personenName.setNamensKlassifizierung(new NamensKlassifizierungEntityFactory().createNachnameKlassifizierung());
        personenName.setReihenfolge(2);
        return personenName;
    }

    public List<PersonenName> createRandomName() {
        final ArrayList<PersonenName> name = new ArrayList<PersonenName>();
        name.add(createRandomVorname());
        name.add(createRandomNachname());
        return name;
    }

    public static String namenslisteToString(final List<PersonenName> personenNameList) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < personenNameList.size(); i++) {
            final PersonenName personenName = personenNameList.get(i);
            // QUEST personenName.getReihenfolge() berücksichtigen?
            sb.append(personenName.getName());
            if (i < personenNameList.size() - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    @Test
    public void testnamenslisteToString() throws Exception {
        final List<PersonenName> name = createRandomName();
        final String nameStr = namenslisteToString(name);
        System.out.println(nameStr);
    }
}
