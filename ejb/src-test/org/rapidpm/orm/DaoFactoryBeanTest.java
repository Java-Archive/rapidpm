/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm;

import org.apache.log4j.Logger;
import org.rapidpm.data.LoggingResult;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.system.logging.LoggingEventEntry;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 08.12.11
 * Time: 17:00
 */
public class DaoFactoryBeanTest<T> extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(DaoFactoryBeanTest.class);

    private final Class<T> daoBeanClass;
    protected T daoBean;

    // REFAC <DFB extends DaoFactoryBean>
    public <DFB extends DaoFactory> DaoFactoryBeanTest(final Class<T> daoBeanClass, final Class<DFB> daoFactoryBeanClass) {
        this.daoBeanClass = daoBeanClass;
        init(daoFactoryBeanClass);
    }

    protected static void setField(final Class clazz, final String name, final Object obj, final Object value)
            throws NoSuchFieldException, IllegalAccessException {
        final Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private <DFB> void init(final Class<DFB> daoFactoryBeanClass) {
        try {
            final DFB factoryBean = daoFactoryBeanClass.newInstance();
            setField(daoFactoryBeanClass, "entityManager", factoryBean, daoFactory.getEntityManager());
            setField(daoFactoryBeanClass, "daoFactory", factoryBean, daoFactory); // BUG falsche BaseDAO

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
