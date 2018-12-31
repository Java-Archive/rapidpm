package org.rapidpm.webapp.vaadin.ui;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class AbstractView extends HorizontalLayout {

    public AbstractView() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);

        add(layout);
        setMargin(false);
        setSizeFull();
        getElement()
                .getStyle()
                .set("overflow", "auto");
    }

    public abstract String getViewName();
}