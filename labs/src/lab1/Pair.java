package lab1;

public class Pair<K extends Comparable, V> implements Comparable<Pair> {

    private final K key;
    private final V value;

    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(" + key.toString() + ", " + value.toString() + ")";
    }
    
    @Override
    public int compareTo(Pair p) {
        return this.key.compareTo(p.key);
    }
}
