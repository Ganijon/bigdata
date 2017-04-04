package lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ganijon
 */
public class GroupByPair<K, V> {

    private final K key;
    private final List<V> values;

    public GroupByPair(K key) {
        this.key = key;
        this.values = new ArrayList<>();
    }

    public K getKey() {
        return key;
    }

    public void addValue(V value) {
        values.add(value);
    }

    @Override
    public String toString() {
        return "<(" + key.toString() + ", " + values.toString() + ")>";

    }
}
