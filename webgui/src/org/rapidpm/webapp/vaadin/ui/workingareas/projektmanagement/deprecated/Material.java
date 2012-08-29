package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.deprecated;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 13.04.12
 * Time: 13:02
 */
public class Material {

    public static final String[] VISIBLE_COLUMNS = {"bezeichnung", "anzahl", "material", "preis", "lieferant"};
    public static final String[] COLUMN_NAMES = {"Bezeichnung", "Anzahl", "Material", "Preis / Stk", "Lieferant"};

    private String bezeichnung;
    private int anzahl;
    private String material;
    private BigDecimal preis;
    private String lieferant;

    public Material(final String bezeichnung, final int anzahl, final String material, final BigDecimal preis, final String lieferant) {
        this.bezeichnung = bezeichnung;
        this.anzahl = anzahl;
        this.material = material;
        this.preis = preis;
        this.lieferant = lieferant;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(final String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(final int anzahl) {
        this.anzahl = anzahl;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(final String material) {
        this.material = material;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(final BigDecimal preis) {
        this.preis = preis;
    }

    public String getLieferant() {
        return lieferant;
    }

    public void setLieferant(final String lieferant) {
        this.lieferant = lieferant;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Material");
        sb.append("{bezeichnung='").append(bezeichnung).append('\'');
        sb.append(", anzahl=").append(anzahl);
        sb.append(", material='").append(material).append('\'');
        sb.append(", preis=").append(preis);
        sb.append(", lieferant='").append(lieferant).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
