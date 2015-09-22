package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

public class AddRowClickListener implements ClickListener {
  private MainUI ui;
  private StundensaetzeScreen screen;

  public AddRowClickListener(final MainUI ui, final StundensaetzeScreen screen) {
    this.ui = ui;
    this.screen = screen;
  }

  @Override
  public void buttonClick(final ClickEvent event) {
    final AddRowLogic logic = new AddRowLogic(ui, screen);
    logic.execute();
  }

}
