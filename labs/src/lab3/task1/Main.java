package lab3.task1;

import java.util.List;
import java.util.Map;
import lab2.task1.GroupByPair;
import lab2.task1.Pair;

/**
 *
 * @author Ganijon
 */
public class Main {
     public static void main(String[] args) throws NumberFormatException {
         
         WordCount wc = new WordCount();
         wc.init();
         
         Map<Integer, List<Pair>> mapOut = wc.map();
         Map<Integer, List<Pair>> partitionOut = wc.partition(mapOut);
         Map<Integer, List<GroupByPair>> groupOut = wc.group(partitionOut);
         Map<Integer, List<Pair>> reduceOut = wc.reduce(groupOut);
         
     }
}
