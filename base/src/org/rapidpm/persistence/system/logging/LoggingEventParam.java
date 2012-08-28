package org.rapidpm.persistence.system.logging;

import javax.persistence.*;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 14.01.2010
 *        Time: 14:54:15
 */

//import org.apache.log4j.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

@Entity
//@Table(name = "logging_event_param")
public class LoggingEventParam {
    // private static final Logger logger = Logger.getLogger(LoggingEventParam.class);
    // public static final Log logger = LogFactory.getLog(LoggingEventParam.class);

    @Id
    @TableGenerator(name = "PKGenLoggingEventParam", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "LoggingEventParam_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenLoggingEventParam")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String paramName;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(final String paramName) {
        this.paramName = paramName;
    }

    @Column(columnDefinition = "TEXT")
    @Basic
    private String paramValue;

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(final String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoggingEventParam)) {
            return false;
        }

        final LoggingEventParam that = (LoggingEventParam) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (!paramName.equals(that.paramName)) {
            return false;
        }
        if (!paramValue.equals(that.paramValue)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + paramName.hashCode();
        result = 31 * result + paramValue.hashCode();
        return result;
    }
}