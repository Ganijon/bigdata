package lab3.task1;

/**
 *
 * @author Ganijon
 */
public class Reducer {

    public Pair reduce(GroupByPair<?, Integer> grouped) {

        int sum = 0;

        for (Integer i : grouped.getValues()) {
            sum += i;
        }
        
        return new Pair(grouped.getKey(), sum);
    }
}