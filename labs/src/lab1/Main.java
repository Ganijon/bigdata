package lab1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Ganijon
 */
public class Main {

    private static final String FILE = "/lab1data.txt";
    private static Scanner scanner;
    private static List<Pair> list;

    public static void main(String[] args) throws IOException {

        list = new ArrayList<>();
        scanner = new Scanner(Main.class.getResourceAsStream(FILE));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (String token : line.split(" ")) {
                for (String word : token.split("-")) {
                    if (word.length() > 0
                            && word.chars().allMatch(Character::isLetter)) {
                        list.add(new Pair(word.toLowerCase(), 1));
                    }
                }
            }
        }

        Collections.sort(list);
        list.stream().forEach((pair) -> {
            System.out.println(pair);
        });
    }
}
