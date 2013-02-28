package org.rapidpm.webservice.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webservice.mapping.FlatEntity;

public class FlatPlannedProject extends FlatEntity<PlannedProject> {
    // TODO
    private String projektName;
    private String projektToken;
    private Long creatorId;
    private Long responsiblePersonId;
    private String info;

    public String getProjektName() {
        return projektName;
    }

    public void setProjektName(final String projektName) {
        this.projektName = projektName;
    }

    public String getProjektToken() {
        return projektToken;
    }

    public void setProjektToken(final String projektToken) {
        this.projektToken = projektToken;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(final Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(final Long responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(final String info) {
        this.info = info;
    }

    @Override
    public void fromEntity(final PlannedProject plannedProject) {
        id = plannedProject.getId();
        projektName = plannedProject.getProjektName();
        projektToken = plannedProject.getProjektToken();
        creatorId = getId(plannedProject.getCreator());
        responsiblePersonId = getId(plannedProject.getResponsiblePerson());
        info = plannedProject.getInfo();
    }

    @Override
    public void toEntity(final PlannedProject plannedProject, final DaoFactory daoFactory) {
        plannedProject.setProjektName(projektName);
        plannedProject.setProjektToken(projektToken);
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        plannedProject.setCreator(benutzerDAO.findByID(creatorId));
        plannedProject.setResponsiblePerson(benutzerDAO.findByID(responsiblePersonId));
        plannedProject.setInfo(info);
    }
}
