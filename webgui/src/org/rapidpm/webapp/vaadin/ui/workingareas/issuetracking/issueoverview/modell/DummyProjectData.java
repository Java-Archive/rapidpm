package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: Alvin Schiller
* Date: 27.09.12
* Time: 13:36
* To change this template use File | Settings | File Templates.
*/
public class DummyProjectData {

    public final static String PROPERTY_CAPTION = "caption";

    public static List<String> getTypeList() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Bug");
        statusList.add("Task");
        statusList.add("Improvement");
        statusList.add("New Function");
        statusList.add("Epic");
        statusList.add("Story");
        return statusList;
    }

    public static List<Integer> getStoryPointArray() {
        return new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
    }

    public static List<String> getVersionArray() {
        return new ArrayList<>(Arrays.asList("1.0", "2.0"));
    }

    public static List<Integer> getRiskArray() {
        return new ArrayList<>(Arrays.asList(0,25,50,75,100));
    }
}
