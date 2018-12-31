package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class EditGroupValueChangeListener  {

//    private OptionGroup editGroup;
    private Button saveButton;
    private Grid tabelle;
    private HorizontalLayout saveButtonLayout;
    private ResourceBundle messages;
    private StundensaetzeScreen screen;
    private List<ItemClickDependentComponent> components;
    private Button deleteButton;

    private static final Logger logger = Logger.getLogger(EditGroupValueChangeListener.class);

    public EditGroupValueChangeListener(final StundensaetzeScreen screen, List<ItemClickDependentComponent>
            components, final Button deleteButton, final ResourceBundle bundle,
                                        final HorizontalLayout saveButtonLayout,
                                        final Button saveButton, final Grid tabelle) {
        this.screen = screen;
        this.components = components;
        this.deleteButton = deleteButton;
        this.messages = bundle;
//        this.editGroup = group;
        this.saveButton = saveButton;
        this.tabelle = tabelle;
        this.saveButtonLayout = saveButtonLayout;
    }

//    @Override
//    public void valueChange(final ValueChangeEvent event) {
//        final String TABLEMODE = messages.getString("stdsatz_tablemode");
//        final Property property = event.getProperty();
//        final Object value = property.getValue();
//        if (value != null) {
//            if (value.equals(TABLEMODE)) {
//                EditModeGetter.setMode(EditModes.TABLEEDIT);
//            } else {
//                EditModeGetter.setMode(EditModes.ROWEDIT);
//            }
//            editMethod(EditModeGetter.getMode());
//        }
//
//    }

    private void editMethod(final EditModes mode) {
//        final EditGroupValueChangeListenerBean editGroupValueChangeListenerBean = EJBFactory
//                .getEjbInstance(EditGroupValueChangeListenerBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = editGroupValueChangeListenerBean.getDaoFactoryBean();

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//        for (final Object listener : saveButton.getListeners(Component.Event.class)) {
//            if (listener instanceof Button.ClickListener) {
//                saveButton.removeClickListener((Button.ClickListener) listener);
//            }
//        }
//        for(final Object listener : tabelle.getListeners(Component.Event.class)){
//            if (listener instanceof ItemClickEvent.ItemClickListener) {
//                tabelle.removeItemClickListener((ItemClickEvent.ItemClickListener) listener);
//            }
//        }
//
//        if(mode.equals(EditModes.TABLEEDIT)){
//            tabelle.setValue(null);
//            saveButtonLayout.setVisible(true);
//            tabelle.setReadOnly(true);
//            tabelle.setTableFieldFactory(new TableEditFieldFactory());
//            tabelle.setEditable(true);
//            tabelle.setSelectable(false);
//
//
//            saveButton.addClickListener(new Button.ClickListener() {
//                @Override
//                public void buttonClick(Button.ClickEvent event) {
//                    try {
//                        for(RessourceGroup ressourceGroup : (Collection<RessourceGroup>) tabelle.getItemIds()){
//                            daoFactory.saveOrUpdate(ressourceGroup);
//                        }
//                        screen.generateTableAndCalculate();
//                        saveButtonLayout.setVisible(false);
//                        tabelle.setEditable(false);
//                        editGroup.setValue(messages.getString("stdsatz_rowmode"));
//                    } catch (Exception e) {
//                        logger.warn("Exception", e);
//                    }
//                }
//            });
//        } else {
//            tabelle.setReadOnly(false);
//            tabelle.setSelectable(true);
//            tabelle.setEditable(false);
//            saveButtonLayout.setVisible(false);
//            tabelle.addItemClickListener(new StundensaetzeItemClickListener(components, deleteButton,
//                    saveButtonLayout, tabelle));
//
//            saveButton.addClickListener(new Button.ClickListener() {
//
//                @Override
//                public void buttonClick(Button.ClickEvent event) {
//                    try {
//
//                        //RessourceGroup in DB updaten
//                        final RessourceGroup ressouregroupFromTable = (RessourceGroup)tabelle.getValue();
//                        final RessourceGroup ressourceGroupFromDB = daoFactory.getRessourceGroupDAO().findByID
//                                (ressouregroupFromTable.getId());
//                        tabelle.commit();
//                        daoFactory.saveOrUpdateTX(ressouregroupFromTable);
//                        screen.generateTableAndCalculate();
//
//                        saveButtonLayout.setVisible(false);
//                        tabelle.setEditable(false);
//                        editGroup.setValue(messages.getString("stdsatz_rowmode"));
//
//                    } catch (final Exception e) {
//                        logger.warn("Exception", e);
//                    }
//                }
//            });
//        }
    }

}
