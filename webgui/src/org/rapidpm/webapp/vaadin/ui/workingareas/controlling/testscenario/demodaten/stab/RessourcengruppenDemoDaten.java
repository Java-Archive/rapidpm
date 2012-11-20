package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.stab.RessourceGroupBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class RessourcengruppenDemoDaten {
    private RessourceGroup scrumMaster;
    private RessourceGroup seniorProjectCollaborator;
    private RessourceGroup juniorProjectCollaborator;
    private RessourceGroup workingStudent;


    public RessourcengruppenDemoDaten() {
        scrumMaster = new RessourceGroupBuilder()
                .setBruttoJahresGehalt(72_000)
                .setRessourcenGruppenName("Projektleiter / Scrum Master")
                .getRessourceGroup();

        seniorProjectCollaborator = new RessourceGroupBuilder()
                .setBruttoJahresGehalt(72_000)
                .setRessourcenGruppenName("Senior Projektmitarbeiter")
                .getRessourceGroup();

        juniorProjectCollaborator = new RessourceGroupBuilder()
                .setBruttoJahresGehalt(45_000)
                .setRessourcenGruppenName("Junior Projektmitarbeiter")
                .getRessourceGroup();

        workingStudent = new RessourceGroupBuilder()
                .setBruttoJahresGehalt(17_000)
                .setRessourcenGruppenName("Aushilfe/stud. Kraft")
                .getRessourceGroup();
    }

    public RessourceGroup getScrumMaster() {
        return scrumMaster;
    }

    public RessourceGroup getSeniorProjectCollaborator() {
        return seniorProjectCollaborator;
    }

    public RessourceGroup getJuniorProjectCollaborator() {
        return juniorProjectCollaborator;
    }

    public RessourceGroup getWorkingStudent() {
        return workingStudent;
    }
}
