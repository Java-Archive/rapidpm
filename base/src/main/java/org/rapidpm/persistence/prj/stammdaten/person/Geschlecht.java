/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence.prj.stammdaten.person;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 20.12.11
 * Time: 14:21
 */

public class Geschlecht {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    private String geschlecht;

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(final String geschlecht) {
        this.geschlecht = geschlecht;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Geschlecht that = (Geschlecht) o;

        if (geschlecht != null ? !geschlecht.equals(that.geschlecht) : that.geschlecht != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Geschlecht");
        sb.append("{id=").append(id);
        sb.append(", geschlecht='").append(geschlecht).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
