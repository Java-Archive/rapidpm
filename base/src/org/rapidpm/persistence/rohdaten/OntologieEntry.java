package org.rapidpm.persistence.rohdaten;

import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class OntologieEntry {

    @Id
    @TableGenerator(name = "PKGenOntologieEntry", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "OntologieEntry_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenOntologieEntry")
    private Long id;

    @Basic
    private String value;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private OntologieConnection connection;


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    public OntologieEntry(final String value, final OntologieConnection connection) {
        this.value = value;
        this.connection = connection;
    }

    public OntologieEntry() {
    }

    @Override
    public boolean equals(final Object o) {
        if (null == o) {
            return true;
        }
        if (!(o instanceof OntologieEntry)) {
            return false;
        }

        final OntologieEntry entry = (OntologieEntry) o;

        if (getId() != entry.getId()) {
            return false;
        }
        if (connection != entry.connection) {
            return false;
        }
        if (!value.equals(entry.value)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + value.hashCode();
        result = 31 * result + connection.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OntologieEntry");
        sb.append("{id=").append(getId());
        sb.append(", value='").append(value).append('\'');
        sb.append(", connection=").append(connection);
        sb.append('}');
        return sb.toString();
    }


    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public OntologieConnection getConnection() {
        return connection;
    }

    public void setConnection(final OntologieConnection connection) {
        this.connection = connection;
    }
}
