package lab2.task1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Grouper {

    public List<GroupByPair> group(List<Pair> list) {

        List<GroupByPair> groupList = new ArrayList<>();

        for (Pair p : list) {

            boolean found = false;

            for (GroupByPair g : groupList) {
                if (g.getKey().equals(p.getKey())) {
                    found = true;
                    g.addValue(1);
                    break;
                }
            }

            if (!found) {
                groupList.add(new GroupByPair(p.getKey(), p.getValue()));
            }
        }

        return groupList;
    }
}