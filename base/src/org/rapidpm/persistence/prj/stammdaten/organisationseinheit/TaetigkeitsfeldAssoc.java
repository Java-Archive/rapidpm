package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 10.03.2010
 *        Time: 08:17:44
 */

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class TaetigkeitsfeldAssoc {
    private static final Logger logger = Logger.getLogger(TaetigkeitsfeldAssoc.class);

    @Id
    @TableGenerator(name = "PKGenTaetigkeitsfeldAssoc", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "TaetigkeitsfeldAssoc_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenTaetigkeitsfeldAssoc")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @OneToOne(fetch = FetchType.EAGER)
    private Taetigkeitsfeld taetigkeitsfeld;

    @OneToOne(fetch = FetchType.EAGER)
    private TaetigkeitsfeldKlassifizierung klassifizierung;

    @OneToOne(fetch = FetchType.EAGER)
    private Mandantengruppe mandantengruppe;


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaetigkeitsfeldAssoc)) {
            return false;
        }

        final TaetigkeitsfeldAssoc that = (TaetigkeitsfeldAssoc) o;

        if (getId() != that.getId()) {
            return false;
        }
        if (!klassifizierung.equals(that.klassifizierung)) {
            return false;
        }
        if (!taetigkeitsfeld.equals(that.taetigkeitsfeld)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + taetigkeitsfeld.hashCode();
        result = 31 * result + klassifizierung.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TaetigkeitsfeldAssoc");
        sb.append("{id=").append(getId());
        sb.append(", taetigkeitsfeld=").append(taetigkeitsfeld);
        sb.append(", klassifizierung=").append(klassifizierung);
        sb.append('}');
        return sb.toString();
    }

    public Taetigkeitsfeld getTaetigkeitsfeld() {
        return taetigkeitsfeld;
    }

    public void setTaetigkeitsfeld(final Taetigkeitsfeld taetigkeitsfeld) {
        this.taetigkeitsfeld = taetigkeitsfeld;
    }

    public TaetigkeitsfeldKlassifizierung getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final TaetigkeitsfeldKlassifizierung klassifizierung) {
        this.klassifizierung = klassifizierung;
    }


    public Mandantengruppe getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }
}
