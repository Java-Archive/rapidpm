/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence;

import org.rapidpm.data.LoggingResult;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 08.12.11
 * Time: 17:00
 */
public class BaseDAOBeanTest<T> extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(BaseDAOBeanTest.class);

    private final Class<T> daoBeanClass;
    protected T daoBean;

    // REFAC <DFB extends BaseDaoFactoryBean>
    public <DFB extends BaseDaoFactory> BaseDAOBeanTest(final Class<T> daoBeanClass, final Class<DFB> daoFactoryBeanClass) {
        this.daoBeanClass = daoBeanClass;
        init(daoFactoryBeanClass);
    }

    protected static void setField(final Class clazz, final String name, final Object obj, final Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private <DFB> void init(final Class<DFB> daoFactoryBeanClass) {
        try {
            final DFB factoryBean = daoFactoryBeanClass.newInstance();
            setField(daoFactoryBeanClass, "em", factoryBean, daoFactory.getEntityManager());
            setField(daoFactoryBeanClass, "daoFactory", factoryBean, daoFactory); // BUG falsche BaseDaoFactory

            final LogEventEntryWriterBean logWriter = new LogEventEntryWriterBean();
            setField(LogEventEntryWriterBean.class, "daoFactory", logWriter, factoryBean);

            daoBean = daoBeanClass.newInstance();
            setField(daoBeanClass, "daoFactoryBean", daoBean, factoryBean);
            setField(daoBeanClass, "logEventEntryWriterBean", daoBean, logWriter);
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            logger.error(e);
        }
    }


    public static void printLog(final LoggingResult result) {
        for (final LoggingEventEntry loggingEventEntry : result.getLoggingEventEntries()) {
            System.out.println(loggingEventEntry);
        }

    }
}
