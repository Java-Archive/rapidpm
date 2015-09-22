package org.rapidpm.webapp.vaadin.bootstrap;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.bootstrap.startupactions.DDIVaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Created by svenruppert on 27.08.15.
 */
@WebServlet(urlPatterns = "/*", name = "RapidPM", displayName = "RapidPM")
@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
//@WebInitParam(name = "Resources", value = "http://virit.in/dawn/11")
public class MainUIServlet extends DDIVaadinServlet {


}
