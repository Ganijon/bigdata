package lab2.task1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Mapper {

    public List<Pair> map(String line) {

        List<Pair> mapOut = new ArrayList<>();

        for (String token : line.split(" ")) {
            token = token.replaceAll("[^a-zA-Z0-9 ._-]", "");
            for (String term : token.split("-")) {
                if (term.length() > 0
                        && term.chars().allMatch(Character::isLetter)) {
                    mapOut.add(new Pair(term.toLowerCase(), 1));
                }
            }
        }
        return mapOut;
    }
}
