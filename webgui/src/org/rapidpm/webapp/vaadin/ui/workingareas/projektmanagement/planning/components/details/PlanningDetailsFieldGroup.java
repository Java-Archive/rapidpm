package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase.*;
import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningDetailsFieldGroup extends FieldGroup {

    private List<AbstractField> fieldList = new ArrayList<>();

    private AbstractSelect statusBox;
    private AbstractSelect priorityBox;
    private AbstractSelect reporterBox;
    private AbstractSelect assigneeBox;
    private DateField plannedField;
    private DateField resolvedField;
    private DateField closedField;

    private ResourceBundle messages;
    private PlanningDetailsFieldGroupBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    public PlanningDetailsFieldGroup(final ResourceBundle messages, final IssueBase issueBase) {
        setItemDataSource(new BeanItem<>(issueBase));
        this.messages = messages;
        bean = EJBFactory.getEjbInstance(PlanningDetailsFieldGroupBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        buildForm();
    }

    private void buildForm() {
        refreshEntities(baseDaoFactoryBean);
        final List<IssuePriority> priorities = baseDaoFactoryBean.getIssuePriorityDAO().loadAllEntities();
        final List<IssueStatus> statusList = baseDaoFactoryBean.getIssueStatusDAO().loadAllEntities();
        final List<Benutzer> users = baseDaoFactoryBean.getBenutzerDAO().loadAllEntities();
        AbstractSelect box;
        DateField dateField;
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch(spaltenName){
                case(PRIORITY):
                    box = generateBox(messages.getString("planning_priority"),
                        new BeanItemContainer<>(IssuePriority.class, priorities),IssuePriority.NAME);
                    bind(box, propertyId);
                    for(final Object itemId : box.getItemIds()){
                        final IssuePriority priority = (IssuePriority) itemId;
                        final String resourcePfad = (IMAGES_DIRECTORY + priority.getPriorityFileName());
                        box.setItemIcon(itemId, new ThemeResource(resourcePfad));
                    }
                    priorityBox = box;
                    fieldList.add(box);
                    break;
                case(STATUS):
                    box = generateBox(messages.getString("planning_state"),
                            new BeanItemContainer<>(IssueStatus.class, statusList), IssueStatus.NAME);
                    bind(box, propertyId);
                    for(final Object itemId : box.getItemIds()){
                        final IssueStatus status = (IssueStatus) itemId;
                        final String resourcePfad = (IMAGES_DIRECTORY + status.getStatusFileName());
                        box.setItemIcon(itemId, new ThemeResource(resourcePfad));
                    }
                    statusBox = box;
                    fieldList.add(box);
                    break;
                case(REPORTER):
                    box = generateBox(messages.getString("planning_reporter"),
                            new BeanItemContainer<>(Benutzer.class, users), Benutzer.LOGIN);
                    bind(box, propertyId);
                    reporterBox = box;
                    fieldList.add(box);
                    break;
                case(ASSIGNEE):
                    box = generateBox(messages.getString("planning_assignee"),
                            new BeanItemContainer<>(Benutzer.class, users), Benutzer.LOGIN);
                    bind(box, propertyId);
                    assigneeBox = box;
                    fieldList.add(box);
                    break;
                case (DATE_PLANNED):
                    dateField = generateDateField(messages.getString("planning_planned"));
                    bind(dateField, propertyId);
                    plannedField = dateField;
                    fieldList.add(dateField);
                    break;
                case (DATE_RESOLVED):
                    dateField = generateDateField(messages.getString("planning_resolved"));
                    bind(dateField, propertyId);
                    resolvedField = dateField;
                    fieldList.add(dateField);
                    break;
                case (DATE_CLOSED):
                    dateField = generateDateField(messages.getString("planning_closed"));
                    bind(dateField, propertyId);
                    closedField = dateField;
                    fieldList.add(dateField);
                    break;
                default:
                    break;
            }
        }
    }

    public AbstractSelect generateBox(final String caption, final BeanItemContainer container, final String itemCaptionPropertyId){
        final AbstractSelect box = new ComboBox(caption,container);
        box.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        box.setItemCaptionPropertyId(itemCaptionPropertyId);
        return box;
    }

    public DateField generateDateField(final String caption){
        final DateField dateField = new DateField(caption);
        return dateField;
    }

    private void refreshEntities(final DaoFactoryBean baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
            entityManager.refresh(plannedProject);
        }
        for(final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
            entityManager.refresh(planningUnitElement);
        }
        for(final PlanningUnit planningUnit : baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities()){
            entityManager.refresh(planningUnit);
        }
        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
            entityManager.refresh(ressourceGroup);
        }
        for(final Benutzer benutzer : baseDaoFactoryBean.getBenutzerDAO().loadAllEntities()){
            entityManager.refresh(benutzer);
        }
    }

    public List<AbstractField> getFieldList() {
        return fieldList;
    }

    public AbstractSelect getStatusBox() {
        return statusBox;
    }

    public AbstractSelect getPriorityBox() {
        return priorityBox;
    }

    public AbstractSelect getReporterBox() {
        return reporterBox;
    }

    public AbstractSelect getAssigneeBox() {
        return assigneeBox;
    }

    public DateField getPlannedField() {
        return plannedField;
    }

    public DateField getResolvedField() {
        return resolvedField;
    }

    public DateField getClosedField() {
        return closedField;
    }
}
