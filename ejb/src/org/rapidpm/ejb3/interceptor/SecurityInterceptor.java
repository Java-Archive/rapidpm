/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.ejb3.interceptor;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 25.10.11
 * Time: 12:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.LoggingResult;
import org.rapidpm.exception.RapidPMException;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.system.logging.LogLevelEnum;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.List;

@Logging
@Interceptor
public class SecurityInterceptor {
    private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactory;

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext context) throws Exception {
        final Object[] parameters = context.getParameters();
        if (parameters.length < 3) {
            //Error

        } else {
            final String passwd = (String) parameters[2];
            final Benutzer benutzer = daoFactory.getBenutzerDAO().findByID((Long) parameters[1]);
            if (benutzer == null) {
                //Error
            } else {
                final boolean equals = benutzer.getPasswd().equals(passwd);
                if (equals) {
                    //alles OK
                    final LoggingEventEntry entry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, context.getMethod().getName(), (String) parameters[0], "User/Passwd OK");
                    logEventEntryWriterBean.writeLogEvents(benutzer, entry);
                    //LOGGing ob User/Passwd korrrekt
                    final Object proceed = context.proceed();
                    if (proceed instanceof LoggingResult) {
                        final LoggingResult result = (LoggingResult) proceed;
                        final List<LoggingEventEntry> loggingEventEntries = result.getLoggingEventEntries();
                        logEventEntryWriterBean.writeLogEvents(loggingEventEntries, benutzer);
                        result.getLoggingEventEntries().add(entry);
                    } else {
                        //
                    }
                    return proceed;
                } else {
                    //error
                }
            }
        }
        throw new RapidPMException("User Passwd Kombination falsch.");
    }


}
