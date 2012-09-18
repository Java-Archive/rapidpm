package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModeGetter;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModes;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.RowEditFieldFactory;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.List;

public class StundensaetzeItemClickListener implements ItemClickListener {

    private Logger logger = Logger.getLogger(StundensaetzeItemClickListener.class);

    private List<ItemClickDependentComponent> components = new ArrayList<ItemClickDependentComponent>();

    private boolean state = false;
    private Layout upperFormLayout;
    private Layout lowerFormLayout;
    private Layout formLayout;
    private Button saveButton;
    private Button deleteButton;
    private Table tabelle;
    private StundensaetzeScreen screen;

    public StundensaetzeItemClickListener(final StundensaetzeScreen screen, final List<ItemClickDependentComponent>
                                            components, final Button deleteButton, final Layout upperFormLayout,
                                          final Layout lowerFormLayout, final Layout formLayout,
                                          final Button saveButton, final Table tabelle) {
        this.components = components;
        this.deleteButton = deleteButton;
        this.upperFormLayout = upperFormLayout;
        this.lowerFormLayout = lowerFormLayout;
        this.formLayout = formLayout;
        this.saveButton = saveButton;
        this.tabelle = tabelle;
        this.screen = screen;
        informComponents(state);
    }

    @Override
    public void itemClick(final ItemClickEvent event) {

        if (EditModeGetter.getMode() == EditModes.ROWEDIT) {
            formLayout.setVisible(true);
            lowerFormLayout.setVisible(true);
            upperFormLayout.setVisible(true);
            final RowEditFieldFactory fieldFactory = new RowEditFieldFactory(event.getItem());

            upperFormLayout.removeAllComponents();
            for (final Object listener : saveButton.getListeners(Event.class)) {
                if (listener instanceof ClickListener) {
                    saveButton.removeClickListener((ClickListener) listener);
                }
            }
            deleteButton.setEnabled(true);
            tabelle.setTableFieldFactory(fieldFactory);
            tabelle.setEditable(true);
            saveButton.addClickListener(new ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    try {
                        tabelle.commit();
                        final StammdatenScreensBean stammdatenScreensBean = screen.getStammdatenScreensBean();
                        final DaoFactoryBean baseDaoFactoryBean = stammdatenScreensBean.getDaoFactoryBean();
                        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
                        final BeanItemContainer<RessourceGroupBean> container =
                                (BeanItemContainer<RessourceGroupBean>)
                                tabelle.getContainerDataSource();
                        for(final RessourceGroupBean bean : container.getItemIds()){
                           ressourceGroupDAO.saveOrUpdate(bean.getRessourceGroup());  //TODO RPM-41
                        }
                        screen.generateTableAndCalculate();
                        upperFormLayout.setVisible(false);
                        saveButton.setVisible(false);
                    } catch(Exception e){
                        logger.warn("Exception", e);
                    }
                }
            });
            saveButton.setVisible(true);
            if (event.getItemId() == null)
                state = false;
            else {
                final Object itemId = event.getItemId();
                informComponents(itemId);
                state = true;
            }
            informComponents(state);
        }
    }

    private void informComponents(boolean state) {
        for (final ItemClickDependentComponent component : components) {
            component.getState(state);
        }
    }

    private void informComponents(Object itemId) {
        for (final ItemClickDependentComponent component : components) {
            component.setItemId(itemId);
        }
    }

}
