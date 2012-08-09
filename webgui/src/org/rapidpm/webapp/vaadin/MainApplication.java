package org.rapidpm.webapp.vaadin;

import com.vaadin.ui.MenuBar;
import org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement.AnfragenmanagementWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungWorkingArea;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:41
 */
public class MainApplication extends BaseApplication {

    public MainApplication() {
        super("RapidPM application");
    }

    @Override
    protected void initMenuBar(final MenuBar menuBar) {

        final MenuBar.MenuItem reportings = menuBar.addItem("Analysen", null, null);
        final MenuBar.MenuItem rp_difference = reportings.addItem("Wortraumanalyse", null, null);
        rp_difference.addItem("Difference-Report", null, null);
        rp_difference.addItem("Termmatrix", null, null);

        menuBar.addItem("Anfragenmanagement", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AnfragenmanagementWorkingArea());
            }
        });

        menuBar.addItem("Projektplanung", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ProjektplanungWorkingArea());
            }
        });
    }

}
