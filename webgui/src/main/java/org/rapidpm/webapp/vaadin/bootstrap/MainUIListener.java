package org.rapidpm.webapp.vaadin.bootstrap;

import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;

/**
 * Created by svenruppert on 20.08.15.
 */
@WebListener
public class MainUIListener extends in.virit.WidgetSet {

  public MainUIListener() {
    System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
  }
}
