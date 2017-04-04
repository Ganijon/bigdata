package lab2.mapreduce;

import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Main {

    private static final String DATA_FILE = "/lab1data.txt";
    //private static final String DATA_FILE = "/lab2data.txt";

    public static void main(String[] args) {

        // map
        List<Pair> mapOut = new Mapper().map(DATA_FILE);
        
        System.out.println("\n Mapper Output: \n");
        mapOut.stream().forEach(System.out::println);
        
        // group
        List<GroupByPair> groupOut = new Grouper().group(mapOut);
        
        System.out.println("\n Reducer Input: \n");
        groupOut.stream().forEach(System.out::println);
        
        // reduce
        System.out.println("\n Reducer Output: \n");
        
        for (GroupByPair reduceIn : groupOut) {
            Pair reduceOut = new Reducer().reduce(reduceIn);
            System.out.println(reduceOut);
        }
    }
}
