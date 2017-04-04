package lab2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Reducer {

    public List<GroupByPair> reduce(List<Pair> list) {

        List<GroupByPair> groupedList = new ArrayList<>();

        for (Pair p : list) {

            boolean found = false;
            for (GroupByPair g : groupedList) {
                if (g.getKey().equals(p.getKey())) {
                    g.addValue(1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                GroupByPair gbp = new GroupByPair(p.getKey());
                gbp.addValue(p.getValue());
                groupedList.add(gbp);
            }
        }

        return groupedList;
    }
}
