package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.logic.jira;

import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.jira.generated.JiraSoapServiceServiceLocator;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.jira.generated.JirasoapserviceV2SoapBindingStub;


/**
 * Copyright by Sven Ruppert // chef@sven-ruppert.de
 * <p/>
 * User: svenruppert
 * Date: 18.10.12
 * Time: 07:39
 * <p/>
 * Version:
 */
public abstract class JiraRequestExecutor<T> {

    public static final int TIMEOUT = 60000;
    public final String USER;
    public final String PASSWD;


    protected JiraRequestExecutor(final String user, final String passwd) {
        PASSWD = passwd;
        USER = user;
    }


    private final JiraSoapServiceServiceLocator serviceLocator = new JiraSoapServiceServiceLocator();

    public abstract T executeTask(final String secureToken,final JirasoapserviceV2SoapBindingStub binding)
            throws java.rmi.RemoteException;

    public  T execute() throws javax.xml.rpc.ServiceException {
        try {
            final JirasoapserviceV2SoapBindingStub binding = (JirasoapserviceV2SoapBindingStub) serviceLocator.getJirasoapserviceV2();
            final String secureToken = binding.login(USER, PASSWD);

            final T t = executeTask(secureToken, binding);

            final boolean logout = binding.logout(secureToken);
            return t;
        } catch (java.rmi.RemoteException e) {
            System.out.println("e = " + e);
            //logger.error(e);
        } finally {
            //do
        }
        return null; //TODO refactoring
    }


}
