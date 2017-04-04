package lab2;

import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Main {

    private static final String DATA_FILE = "/lab1data.txt";
    //private static final String DATA_FILE = "/lab2test.txt";

    public static void main(String[] args) {

        List<Pair> mapOut = new Mapper().map(DATA_FILE);
        
        List<GroupByPair> reduceOut = new Reducer().reduce(mapOut);

         reduceOut.stream().forEach((gp) -> {
            System.out.println(gp);
        });

    }
}
