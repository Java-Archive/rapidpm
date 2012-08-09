package org.rapidpm.orm.prj.stammdaten.address;

import org.rapidpm.lang.Pair;
import org.rapidpm.orm.EntityFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 05.10.11
 * Time: 10:11
 */
public class StateFactory extends EntityFactory<State> {
    public final List<Pair<String, String>> states = new ArrayList<Pair<String, String>>();
    public final List<String> klassifizierungen = new ArrayList<String>();

    {
        states.add(new Pair<String, String>("BW", "Baden-Württemberg"));
        states.add(new Pair<String, String>("BY", "Bayern"));
        states.add(new Pair<String, String>("BE", "Berlin"));
        states.add(new Pair<String, String>("BB", "Brandenburg"));
        states.add(new Pair<String, String>("HB", "Bremen"));
        states.add(new Pair<String, String>("HH", "Hamburg"));
        states.add(new Pair<String, String>("HE", "Hessen"));
        states.add(new Pair<String, String>("MV", "Mecklenburg-Vorpommern"));
        states.add(new Pair<String, String>("NI", "Niedersachsen"));
        states.add(new Pair<String, String>("NW", "Nordrhein-Westfalen"));
        states.add(new Pair<String, String>("RP", "Rheinland-Pfalz"));
        states.add(new Pair<String, String>("SL", "Saarland"));
        states.add(new Pair<String, String>("SN", "Sachsen"));
        states.add(new Pair<String, String>("ST", "Sachsen-Anhalt"));
        states.add(new Pair<String, String>("SH", "Schleswig-Holstein"));
        states.add(new Pair<String, String>("TH", "Thüringen"));
    }

    public StateFactory() {
        super(State.class);
    }

    @Override
    public State createRandomEntity() {
        final State state = new State();
        final Pair<String, String> stringPair = RND.nextElement(states);
        state.setKurzName(combineStringWithNextIndex(stringPair.getValue1()));
        state.setName(combineStringWithNextIndex(stringPair.getValue2()));
        state.setLand(new LandFactory().createRandomEntity());
        state.setStateklassifizierung(new StateKlassifizierungFactory().createRandomEntity());
        return state;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
