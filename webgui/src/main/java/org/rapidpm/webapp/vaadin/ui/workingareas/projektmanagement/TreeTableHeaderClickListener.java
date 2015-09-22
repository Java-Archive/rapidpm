package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 05.09.12
 * Time: 08:42
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableHeaderClickListener implements Table.HeaderClickListener {

  private Button undoButton;

  public TreeTableHeaderClickListener(Button undoButton) {
    this.undoButton = undoButton;
  }

  @Override
  public void headerClick(Table.HeaderClickEvent event) {
    undoButton.setVisible(true);
  }
}