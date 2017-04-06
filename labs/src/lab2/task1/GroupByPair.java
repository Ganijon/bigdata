package lab2.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ganijon
 */
public class GroupByPair<K extends Comparable, V> {

    private final K key;
    private final List<V> values;

    public GroupByPair(K key, V...values) {
        this.key = key;
        this.values = new ArrayList<>(Arrays.asList(values));
    }

    public K getKey() {
        return key;
    }

    public List<V> getValues() {
        return values;
    }

    public void addValue(V value) {
        values.add(value);
    }
    
    @Override
    public String toString() {
        return "<(" + key.toString() + ", " + values.toString() + ")>";
    }
}
