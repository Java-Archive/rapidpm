package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import java.util.ResourceBundle;

public class EditGroupValueChangeListener implements ValueChangeListener {

    private OptionGroup editGroup;
    private Button saveButton;
    private Table tabelle;
    private Layout formLayout;
    private Layout upperFormLayout;
    private Layout lowerFormLayout;
    private ResourceBundle messages;
    private StundensaetzeScreen screen;

    public EditGroupValueChangeListener(final StundensaetzeScreen screen, final ResourceBundle bundle,
                                        final Layout formLayout, final Layout upperFormLayout,
                                        final Layout lowerFormLayout, final OptionGroup group,
                                        final Button saveButton, final Table tabelle) {
        this.screen = screen;
        this.messages = bundle;
        this.editGroup = group;
        this.saveButton = saveButton;
        this.tabelle = tabelle;
        this.formLayout = formLayout;
        this.upperFormLayout = upperFormLayout;
        this.lowerFormLayout = lowerFormLayout;
    }

    @Override
    public void valueChange(final ValueChangeEvent event) {
        final String TABLEMODE = messages.getString("stdsatz_tablemode");
        final String ROWMODE = messages.getString("stdsatz_rowmode");
        final Property property = event.getProperty();
        final Object value = property.getValue();
        if (value != null) {
            if (value.equals(TABLEMODE)) {
                formLayout.setVisible(true);
                upperFormLayout.setVisible(false);
                lowerFormLayout.setVisible(true);
                tabelle.setValue(null);
                EditModeGetter.setMode(EditModes.TABLEEDIT);
                saveButton.setVisible(true);
                tabelle.setReadOnly(true);
                tabelle.setTableFieldFactory(new TableEditFieldFactory());
                tabelle.setSelectable(false);
                tabelle.setEditable(true);
                for (final Object listener : saveButton.getListeners(Event.class)) {
                    if (listener instanceof ClickListener) {
                        saveButton.removeClickListener((ClickListener) listener);
                    }

                }
                saveButton.addClickListener(new ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        tabelle.commit();
                        final StammdatenScreensBean screenBean = screen.getStammdatenScreensBean();
                        final DaoFactoryBean baseDaoFactoryBean = screenBean.getDaoFactoryBean();
                        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
                        final BeanItemContainer<RessourceGroup> container = (BeanItemContainer<RessourceGroup>) tabelle
                                .getContainerDataSource();
                        for(final RessourceGroup bean : container.getItemIds()){
                            ressourceGroupDAO.saveOrUpdate(bean);
                        }
                        screen.generateTableAndCalculate();

                        saveButton.setVisible(false);
                        EditModeGetter.setMode(EditModes.ROWEDIT);
                        editGroup.setValue(ROWMODE);
                        tabelle.setEditable(false);
                        tabelle.setSelectable(true);
                    }
                });
            } else {
                EditModeGetter.setMode(EditModes.ROWEDIT);
                tabelle.setReadOnly(false);
                tabelle.setSelectable(true);
                tabelle.setEditable(false);
                upperFormLayout.setVisible(true);
                lowerFormLayout.setVisible(true);
                formLayout.setVisible(false);
            }
        }

    }

}
