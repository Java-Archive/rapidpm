package org.rapidpm.persistence.prj.stammdaten.address; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.03.11
 * Time: 10:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
public class Postfach {
    private static final Logger logger = Logger.getLogger(Postfach.class);

    @Id
    @TableGenerator(name = "PKGenPostfach",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "Postfach_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPostfach")
    private Long id;


    @Basic
    String postfachnummer;
    @Basic
    String notiz;


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Postfach)) {
            return false;
        }

        final Postfach postfach = (Postfach) o;

        if (id != null ? !id.equals(postfach.id) : postfach.id != null) {
            return false;
        }
        if (notiz != null ? !notiz.equals(postfach.notiz) : postfach.notiz != null) {
            return false;
        }
        if (postfachnummer != null ? !postfachnummer.equals(postfach.postfachnummer) : postfach.postfachnummer != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (postfachnummer != null ? postfachnummer.hashCode() : 0);
        result = 31 * result + (notiz != null ? notiz.hashCode() : 0);
        return result;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(final String notiz) {
        this.notiz = notiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getPostfachnummer() {
        return postfachnummer;
    }

    public void setPostfachnummer(final String postfachnummer) {
        this.postfachnummer = postfachnummer;
    }
}
