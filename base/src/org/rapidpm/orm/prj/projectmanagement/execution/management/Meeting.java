package org.rapidpm.orm.prj.projectmanagement.execution.management;

import org.rapidpm.orm.prj.stammdaten.address.Adresse;
import org.rapidpm.orm.prj.stammdaten.person.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 10:02
 * <p/>
 * Eine zeitlich geplante Zusammenkunft
 */
public class Meeting {

    private Adresse adresse;

    private List<Person> participants = new ArrayList<>();
    private Date plannedStart;
    private Date plannedStop;

    private String subject;
    private String comments;


}
