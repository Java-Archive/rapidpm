package org.rapidpm.persistence.prj;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.01.12
 * Time: 21:25
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.LoggingResult;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.security.BenutzerDAOBean;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;

import java.lang.reflect.Field;

public class BaseDAOBeanTest<T> extends BaseDAOTest {

    private static final Logger logger = Logger.getLogger(BaseDAOBeanTest.class);

    private final Class<T> daoBeanClass;
    protected T daoBean;

    public BaseDAOBeanTest(final Class<T> daoBeanClass) {
        this.daoBeanClass = daoBeanClass;
        init();
    }

    protected static void setField(final Class clazz, final String name, final Object obj, final Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private void init() {
        try {
            final DaoFactoryBean factoryBean = new DaoFactoryBean();
            setField(DaoFactoryBean.class, "em", factoryBean, daoFactoryFactory.getEntityManager());
            setField(DaoFactoryBean.class, "daoFactory", factoryBean, daoFactoryFactory);

            final LogEventEntryWriterBean logWriter = new LogEventEntryWriterBean();
            setField(LogEventEntryWriterBean.class, "daoFactory", logWriter, factoryBean);

            daoBean = daoBeanClass.newInstance();
            setField(BenutzerDAOBean.class, "daoFactoryBean", daoBean, factoryBean);
            setField(BenutzerDAOBean.class, "logEventEntryWriterBean", daoBean, logWriter);
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            logger.error(e);
        }
    }


    public static void printLog(final LoggingResult result) {
        for (final LoggingEventEntry loggingEventEntry : result.getLoggingEventEntries()) {
            System.out.println(loggingEventEntry);
        }

    }

    @Override
    public String toString() {
        return "BaseDAOBeanTest{" +
                "daoBeanClass=" + daoBeanClass +
                ", daoBean=" + daoBean +
                '}';
    }
}
