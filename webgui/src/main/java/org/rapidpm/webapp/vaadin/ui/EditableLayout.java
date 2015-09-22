package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 30.08.12
 * Time: 09:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public abstract class EditableLayout extends VerticalLayout {

  protected Button saveButton = new Button();
  protected Button cancelButton = new Button();
  protected ResourceBundle messages;

  protected Layout componentsLayout;
  protected boolean active = false;
  protected HorizontalLayout buttonLayout = new HorizontalLayout();

  public EditableLayout(final Screen screen, final Panel screenPanel) {
    setLayout();
    this.setStyleName("abc");
    this.setMargin(false);
    messages = screen.getMessagesBundle();
    saveButton.setCaption(messages.getString("save"));
    cancelButton.setCaption(messages.getString("cancel"));
    screenPanel.addClickListener(new MouseEvents.ClickListener() {
      @Override
      public void click(MouseEvents.ClickEvent event) {
        if (!active) {
          final Iterator<Component> componentIterator = componentsLayout.iterator();
          while (componentIterator.hasNext()) {
            final Component component = componentIterator.next();

            if (component instanceof Table) {
              if (!((Table) component).isEditable()) {
                ((Table) component).setEditable(true);
              }
            } else if (component instanceof AbstractField) {
              component.setReadOnly(false);
            }
          }
          buttonLayout.setVisible(true);
          active = true;
        }
      }
    });

    buttonLayout.addComponent(saveButton);
    buttonLayout.addComponent(cancelButton);
    buttonLayout.setVisible(false);
    addComponent(componentsLayout);
    addComponent(buttonLayout);
  }

  protected abstract void setLayout();

  protected abstract void buildForm();

  public Layout getComponentsLayout() {
    return componentsLayout;
  }

  public void setComponentsLayout(Layout componentsLayout) {
    this.componentsLayout = componentsLayout;
  }

  public HorizontalLayout getButtonLayout() {
    return buttonLayout;
  }

  public void setButtonLayout(HorizontalLayout buttonLayout) {
    this.buttonLayout = buttonLayout;
  }
}
