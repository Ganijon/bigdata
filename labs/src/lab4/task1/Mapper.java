package lab4.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ganijon
 */
public class Mapper {

    private HashMap<String, Integer> H;

    public void initialize() {
        H = new HashMap<>();
    }

    public void map(String line) {

        for (String token : line.split(" ")) {
            
            token = token.replaceAll("[^a-zA-Z0-9 ._-]", "");
            
            for (String term : token.split("-")) {

                if (term.length() > 0
                        && term.chars().allMatch(Character::isLetter)) {

                    term = term.toLowerCase();

                    int count = 1;
                    if (H.containsKey(term)) {
                        count = H.get(term);
                        count++;
                    }
                    H.put(term, count);
                }
            }
        }
    }

    public List<Pair> close() {
        List<Pair> mapOut = new ArrayList();

        H.keySet().forEach((term) -> {
            mapOut.add(new Pair(term, H.get(term)));
        });

        return mapOut;
    }
}
