package lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ganijon
 */
public class Mapper {
   
    public List<Pair> map(String dataFile) {

        List<Pair> list = new ArrayList<>();
        Scanner scanner = new Scanner(Main.class.getResourceAsStream(dataFile));

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

        return list;
    }
}