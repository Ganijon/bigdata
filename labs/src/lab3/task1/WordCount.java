package lab3.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import lab2.task1.GroupByPair;
import lab2.task1.Grouper;
import lab2.task1.Mapper;
import lab2.task1.Pair;
import lab2.task1.Reducer;

/**
 *
 * @author Ganijon
 */
public class WordCount {

    private final Map<LogKey, List<Pair>> logs = new TreeMap<>();

    private Mapper[] mappers;
    private Reducer[] reducers;

    public void init() {
        Scanner in = new Scanner(System.in);

        System.out.print("Number of Input-Splits: ");
        int nInputSplits = tryParse(in.nextLine(), 3);

        System.out.print("Number of Reducers: ");
        int nReducers = tryParse(in.nextLine(), 3);

        mappers = new Mapper[nInputSplits];
        reducers = new Reducer[nReducers];
    }

    public Map<Integer, List<Pair>> map() {

        String[] inputSplits = new String[mappers.length];
        for (int i = 0; i < inputSplits.length; i++) {
            inputSplits[i] = getInputSplit("/lab3/data" + i + ".txt");
            System.out.printf("Mapper %d Input:\n%s", i, inputSplits[i]);
        }

        Map<Integer, List<Pair>> mapOut = new HashMap<>();
        for (int i = 0; i < mappers.length; i++) {
            mappers[i] = new Mapper();
            List<Pair> pairs = mappers[i].map(inputSplits[i]);
            System.out.printf("Mapper %d Output: \n", i);
            pairs.stream().forEach(System.out::println);
            mapOut.put(i, pairs);
        }
        return mapOut;
    }

    public Map<Integer, List<Pair>> partition(Map<Integer, List<Pair>> mapOut) {

        Map<Integer, List<Pair>> partitionOut = new HashMap();

        for (int mapperId : mapOut.keySet()) {

            for (Pair pair : mapOut.get(mapperId)) {

                int reducerId = getPartition(pair.getKey());

                if (!partitionOut.containsKey(reducerId)) {
                    partitionOut.put(reducerId, new ArrayList<>());
                }
                partitionOut.get(reducerId).add(pair);

                log(mapperId, reducerId, pair);
            }
        }

        printLogs();

        return partitionOut;
    }

    public Map<Integer, List<GroupByPair>> group(Map<Integer, List<Pair>> partitionOut) {

        Map<Integer, List<GroupByPair>> groupOuts = new TreeMap<>();

        for (int reducerId : partitionOut.keySet()) {
            List<Pair> groupIn = partitionOut.get(reducerId);
            List<GroupByPair> groupOut = new Grouper().group(groupIn);
            groupOuts.put(reducerId, groupOut);
        }

        return groupOuts;
    }

    public Map<Integer, List<Pair>> reduce(Map<Integer, List<GroupByPair>> groupOut) {

        Map<Integer, List<Pair>> reduceOuts = new TreeMap<>();

        for (int reducerId : groupOut.keySet()) {
            List<Pair> list = new ArrayList<>();
            for (GroupByPair reduceIn : groupOut.get(reducerId)) {
                Pair reduceOut = new Reducer().reduce(reduceIn);
                list.add(reduceOut);
            }
            reduceOuts.put(reducerId, list);
        }
        
        for (int reducerId : reduceOuts.keySet()) {
            System.out.printf("Reducer %d output:\n", reducerId);
            reduceOuts.get(reducerId).stream().forEach(System.out::println);
        }
        return reduceOuts;
    }

    private int getPartition(Object key) {
        return (int) key.hashCode() % reducers.length;
    }

    private int tryParse(String n, int fallback) {
        int result;
        try {
            result = Integer.parseInt(n);
        } catch (NumberFormatException e) {
            result = fallback;
        }
        return result;
    }

    private String getInputSplit(String datafile) {
        Scanner scanner = new Scanner(getClass().getResourceAsStream(datafile));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        return sb.toString();
    }

    private void log(int mapperId, int reducerId, Pair pair) {

        LogKey logKey = new LogKey(mapperId, reducerId);

        List<Pair> movingPairs = logs.get(logKey);

        if (movingPairs == null) {
            movingPairs = new ArrayList<>();
            logs.put(logKey, movingPairs);
        }
        movingPairs.add(pair);
    }

    private void printLogs() {
        for (LogKey key : logs.keySet()) {
            System.out.printf("Pairs send from Mapper %d to Reducer %d\n", key.mapperId, key.reducerId);
            logs.get(key).stream().forEach(System.out::println);
        }
    }

    private class LogKey implements Comparable<LogKey> {

        private int mapperId;
        private int reducerId;

        LogKey(int mid, int rid) {
            mapperId = mid;
            reducerId = rid;
        }

        @Override
        public int hashCode() {
            int hash = 19;
            hash += 31 * hash + reducerId;
            hash += 31 * hash + mapperId;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final LogKey other = (LogKey) obj;
            if (this.reducerId != other.reducerId) {
                return false;
            }
            if (this.mapperId != other.mapperId) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(LogKey o) {
            if (mapperId == o.mapperId) {
                return Integer.compare(reducerId, o.reducerId);
            }
            return Integer.compare(this.mapperId, o.mapperId);
        }

    }

}
