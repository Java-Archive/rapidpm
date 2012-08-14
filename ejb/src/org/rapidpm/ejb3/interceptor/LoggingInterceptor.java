package org.rapidpm.ejb3.interceptor;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 25.02.11
 * Time: 12:32
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.data.LoggingResult;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.BaseDaoFactoryBean;
import org.rapidpm.orm.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.orm.system.logging.LogLevelEnum;
import org.rapidpm.orm.system.logging.LoggingEventEntry;
import org.rapidpm.orm.system.logging.LoggingEventParam;
import org.rapidpm.orm.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashSet;
import java.util.List;

@Logging
@Interceptor
public class LoggingInterceptor {
    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class);


    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactory;


    @AroundInvoke
    public Object aroundInvoke(final InvocationContext context) throws Exception {
        final Object[] parameters = context.getParameters();
        final Benutzer benutzer = daoFactory.getBenutzerDAO().findByID((Long) parameters[1]);
        final String methodname = context.getMethod().getName();
        final String sessionid = (String) parameters[0];
        final LoggingEventEntry entry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.DEBUG, methodname, sessionid, "Methodenaufruf");
        entry.setParameter(new HashSet<LoggingEventParam>());
//        for(int i = 0, parametersLength = parameters.length; i < parametersLength; i++){
//            final Object parameter = parameters[i];
//            final LoggingEventParam param = new LoggingEventParam();
//            param.setParamName("param " + i);
//            param.setParamValue(ToStringBuilder.reflectionToString(parameter, ToStringStyle.SHORT_PREFIX_STYLE));
//            entry.getParameter().add(param);
//            if(logger.isInfoEnabled()){
//                logger.info("param " + i + " -- " + ToStringBuilder.reflectionToString(parameter, ToStringStyle.MULTI_LINE_STYLE));
//            }
//        }
//        if(parameters.length >= 2){
//            final LoggingEventParam param = new LoggingEventParam();
//            param.setParamName("UID");
//            param.setParamValue(((Long) parameters[1]).toString());
//            entry.getParameter().add(param);
//
//        }else{
//            final LoggingEventParam param = new LoggingEventParam();
//            param.setParamName("zu wenig Parameter ");
//            param.setParamValue(parameters.length + "");
//            entry.getParameter().add(param);
//
//        }
//        logEventEntryWriterBean.writeLogEvents(benutzer, entry); //REFAC Performance erhöhen

        final Object proceed = context.proceed();
        if (proceed instanceof LoggingResult) {
            final LoggingResult result = (LoggingResult) proceed;
            final List<LoggingEventEntry> loggingEventEntries = result.getLoggingEventEntries();
//            logEventEntryWriterBean.writeLogEvents(loggingEventEntries, benutzer);   //REFAC Performance erhöhen
            result.getLoggingEventEntries().add(entry);
        } else {

        }
        return proceed;
    }

}
