package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.flow.component.button.Button;

public class ButtonComponent extends Button implements
        ItemClickDependentComponent {
    private Object itemId;

    public ButtonComponent(String s) {
        super(s);
    }

    @Override
    public void setEnabled(boolean state) {
        super.setEnabled(state);
    }

    @Override
    public void setItemId(Object itemId) {
        this.itemId = itemId;
    }

    public Object getItemId() {
        return itemId;
    }

}
