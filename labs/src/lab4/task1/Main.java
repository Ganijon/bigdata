package lab4.task1;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Ganijon
 */
public class Main {
     public static void main(String[] args) throws NumberFormatException {
         
         InMapperWordCount imwc = new InMapperWordCount();
         imwc.init();
         
         Map<Integer, List<Pair>> mapOut = imwc.map();
         Map<Integer, List<Pair>> partitionOut = imwc.partition(mapOut);
         Map<Integer, List<GroupByPair>> groupOut = imwc.group(partitionOut);
         Map<Integer, List<Pair>> reduceOut = imwc.reduce(groupOut);
         
     }
}
