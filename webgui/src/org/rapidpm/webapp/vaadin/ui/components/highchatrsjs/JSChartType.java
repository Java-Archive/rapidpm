package org.rapidpm.webapp.vaadin.ui.components.highchatrsjs;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 05.11.12
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public enum JSChartType {
    LINE("line"),
    SPLINE("spline"),
    AREA("area"),
    AREASPLINE("areaspline"),
    COLUMN("column"),
    BAR("bar"),
    PIE("pie"),
    SCATTER("scatter");

    private String chartType;
    private JSChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartType() {
        return chartType;
    }
}
