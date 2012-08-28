package org.rapidpm.persistence.system.logging;

import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 13.01.2010
 *        Time: 16:57:57
 */

//import org.apache.log4j.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

@Entity
//@Table(name = "logging_event_entry")
public class LoggingEventEntry {
    // private static final Logger logger = Logger.getLogger(LoggingEventEntry.class);
    // public static final Log logger = LogFactory.getLog(LoggingEventEntry.class);

    @Id
    @TableGenerator(name = "PKGenLoggingEventEntry",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "LoggingEventEntry_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenLoggingEventEntry")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Benutzer benutzer;

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(final Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    @Basic
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    private String sessionID;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(final String sessionID) {
        this.sessionID = sessionID;
    }

    @Basic
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    @Basic
    private String methodName;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }

    @Basic
    private String logLevel;

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(final String logLevel) {
        this.logLevel = logLevel;
    }

    @Basic
    private String loggingMessage;

    public String getLoggingMessage() {
        return loggingMessage;
    }

    public void setLoggingMessage(final String loggingMessage) {
        this.loggingMessage = loggingMessage;
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<LoggingEventParam> parameter;

    public Set<LoggingEventParam> getParameter() {
        return parameter;
    }

    public void setParameter(final Set<LoggingEventParam> parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoggingEventEntry)) {
            return false;
        }

        final LoggingEventEntry that = (LoggingEventEntry) o;

        if (!benutzer.equals(that.benutzer)) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (sessionID != null ? !sessionID.equals(that.sessionID) : that.sessionID != null) {
            return false;
        }
        if (!timestamp.equals(that.timestamp)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + benutzer.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + (sessionID != null ? sessionID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LoggingEventEntry");
        sb.append("{id=").append(id);
        if (benutzer != null) {
            sb.append(", benutzer=").append(benutzer.getId());
        } else {
            sb.append(", benutzer = null");
        }
        sb.append(", timestamp=").append(timestamp);
        sb.append(", sessionID='").append(sessionID).append('\'');
        sb.append(", className='").append(className).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", logLevel='").append(logLevel).append('\'');
        sb.append(", loggingMessage='").append(loggingMessage).append('\'');
        sb.append(", parameter=").append(parameter);
        sb.append('}');
        return sb.toString();
    }
}