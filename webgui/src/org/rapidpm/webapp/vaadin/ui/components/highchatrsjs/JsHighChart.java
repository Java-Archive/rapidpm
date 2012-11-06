package org.rapidpm.webapp.vaadin.ui.components.highchatrsjs;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 27.10.12
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */




// KLAPPT NICHT -> Vorsicht;  Keine Exception, GUI friert im extremfall einfach ein
//@JavaScript({   "/VAADIN/javascript/jquery-1.8.2.min.js",
//                "/VAADIN/javascript/highcharts.js",
//                "/VAADIN/javascript/js_highcharts_connector.js"})

//Von lokalem lampp laden:
//@JavaScript({
//        "http://localhost/highcharts/js/jquery-1.8.2.min.js",
//        "http://localhost/highcharts/js/highcharts.js",
//        "http://localhost/highcharts/js/js_highcharts_connector.js"
//        })


@JavaScript({   "../../VAADIN/javascript/libs/jquery/jquery-1.8.2.min.js",
                "../../VAADIN/javascript/libs/highchartsjs/highcharts.js",
                "../../VAADIN/connectors/highchartsjs/js_highcharts_connector.js"})

// KLAPPT, WENN Daten im gleichen Verzeichnis unter /src liegen
//@JavaScript({   "jquery-1.8.2.min.js",
//                "highcharts.js",
//                "js_highcharts_connector.js"
//})
public class JsHighChart extends AbstractJavaScriptComponent
{

    public JsHighChart(){
        System.out.println(getState().config);
    }

    @Override
    public JsHighChartState getState()
    {
        return (JsHighChartState) super.getState();
    }
}
