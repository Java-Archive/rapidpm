package org.rapidpm.persistence.prj.stammdaten.person;

import org.rapidpm.persistence.EntityFactory;
import org.junit.Test;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 12.10.11
 * Time: 13:40
 */
public class PersonEntityFactory extends EntityFactory<Person> {
    public PersonEntityFactory() {
        super(Person.class);
    }

    @Override
    public Person createRandomEntity() {
        final Person person = new Person();
//        person.setGeburtsdatum(new SimpleDateFormat(Constants.YYYY_MM_DD).format(RND.nextDate()));
        person.setGeburtsdatum(new Date());
        // TODO person.setGeschlecht(RND.nextInt(1, 2));
        person.setAnrede(new AnredeEntityFactory().createRandomEntity());
        person.setNamen(new PersonenNameEntityFactory().createRandomName());
//        person.setBenutzer(new BenutzerEntityFactory().createRandomEntityList(1)); // zirkuläre Abhängigkeit!
        // TODO person.setKommunikationsServiceUIDs(null);
        // TODO person.setAdressen(null);
        // TODO person.setWebdomains(null);
        person.setTitel(new TitelEntityFactory().createRandomEntityList(0, 1));
        return person;
    }

    @Test
    public void test() throws Exception {
        final Person person = new PersonEntityFactory().createRandomEntity();
        System.out.println(person);
    }
}
