package lab3.task1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Combiner {

    public List<GroupByPair> combine(List<Pair> list) {

        List<GroupByPair> combinedList = new ArrayList<>();

        for (Pair p : list) {

            boolean found = false;

            for (GroupByPair g : combinedList) {
                if (g.getKey().equals(p.getKey())) {
                    found = true;
                    g.addValue(1);
                    break;
                }
            }

            if (!found) {
                combinedList.add(new GroupByPair(p.getKey(), p.getValue()));
            }
        }

        return combinedList;
    }
}