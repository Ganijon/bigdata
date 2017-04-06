package lab2.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ganijon
 */
public class Main {

    private static final String DATA_FILE = "/lab1/data.txt";
    //private static final String DATA_FILE = "/lab2/data.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(Main.class.getResourceAsStream(DATA_FILE));

        // map
        List<Pair> mapOut = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            mapOut.addAll(new Mapper().map(line));
        }
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
