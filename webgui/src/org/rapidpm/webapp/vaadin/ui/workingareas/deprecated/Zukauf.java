package org.rapidpm.webapp.vaadin.ui.workingareas.deprecated;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 13.04.12
 * Time: 13:02
 */
public class Zukauf {

    public static final String[] VISIBLE_COLUMNS = {"was", "wer"};
    public static final String[] COLUMN_NAMES = {"Was", "Wer"};

    private String was;
    private String wer;

    public Zukauf(final String was, final String wer) {
        this.was = was;
        this.wer = wer;
    }

    public String getWas() {
        return was;
    }

    public void setWas(final String was) {
        this.was = was;
    }

    public String getWer() {
        return wer;
    }

    public void setWer(final String wer) {
        this.wer = wer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Zukauf");
        sb.append("{wer='").append(wer).append('\'');
        sb.append(", was='").append(was).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
