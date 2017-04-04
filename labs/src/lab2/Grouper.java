package lab2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Grouper {

    public List<GroupByPair> group(List<Pair> list) {

        List<GroupByPair> groupedList = new ArrayList<>();

        for (Pair p : list) {

            boolean found = false;

            for (GroupByPair g : groupedList) {
                if (g.getKey().equals(p.getKey())) {
                    found = true;
                    g.addValue(1);
                    break;
                }
            }

            if (!found) {
                groupedList.add(new GroupByPair(p.getKey(), p.getValue()));
            }
        }

        return groupedList;
    }
}
