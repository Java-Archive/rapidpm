package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.List;
import java.util.ResourceBundle;

import static com.vaadin.flow.component.HasValue.ValueChangeEvent;
import static com.vaadin.flow.component.HasValue.ValueChangeListener;

public class EditGroupValueChangeListener
    implements ValueChangeListener {

  private Button                            saveButton;
  private Grid                              tabelle;
  private VerticalLayout                    saveButtonLayout;
  private ResourceBundle                    messages;
  private StundensaetzeScreen               screen;
  private List<ItemClickDependentComponent> components;
  private Button                            deleteButton;


  public EditGroupValueChangeListener(final StundensaetzeScreen screen,
                                      List<ItemClickDependentComponent> components,
                                      final Button deleteButton,
                                      final ResourceBundle bundle,
                                      final VerticalLayout saveButtonLayout,
                                      final Button saveButton,
                                      final Grid tabelle) {
    this.screen           = screen;
    this.components       = components;
    this.deleteButton     = deleteButton;
    this.messages         = bundle;
    this.saveButton       = saveButton;
    this.tabelle          = tabelle;
    this.saveButtonLayout = saveButtonLayout;
  }

  private void editMethod(final EditModes mode) {
//        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//        saveButton.
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
//                        screen.refreshGridAndRelatedContent();
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
//                        //RessourceGroup in DB updaten
//                        final RessourceGroup ressouregroupFromTable = (RessourceGroup)tabelle.getSelectedItems().iterator().next();
//                        daoFactory.saveOrUpdateTX(ressouregroupFromTable);
//                        screen.refreshGridAndRelatedContent();
//
//                        saveButtonLayout.setVisible(false);
//                        tabelle.setEnabled(false);
//                        op.setValue(messages.getString("stdsatz_rowmode"));
//
//                    } catch (final Exception e) {
//                        logger.warn("Exception", e);
//                    }
//                }
//            });
//        }
  }

  @Override
  public void valueChanged(ValueChangeEvent valueChangeEvent) {
    final String TABLEMODE = messages.getString("stdsatz_tablemode");
    final Object value     = valueChangeEvent.getValue();
    if (value != null) {
      if (value.toString()
               .equals(TABLEMODE)) {
        EditModeGetter.setMode(EditModes.TABLEEDIT);
      } else {
        EditModeGetter.setMode(EditModes.ROWEDIT);
      }
      editMethod(EditModeGetter.getMode());
    }
  }
}
