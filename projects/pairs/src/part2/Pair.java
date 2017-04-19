package part2;

/**
 *
 * @author 985565
 */
public class Pair {

    String left;
    String right;

    public Pair(String left, String right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right + ")";
    }
}
