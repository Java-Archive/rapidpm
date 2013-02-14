

import com.vaadin.data.util.HierarchicalContainer;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 12.02.13
 * Time: 12:19
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class HierarchicalContainerTest {

    @Test
    public void test001() {
        HierarchicalContainer container = new HierarchicalContainer();

        PlanningUnit unit1 = new PlanningUnit();
        unit1.setId(1l);
        container.addItem(unit1);

        for (Object o : container.getItemIds()) {
            PlanningUnit unit = (PlanningUnit) o;
            System.out.println(unit.toString());
        }
    }
}