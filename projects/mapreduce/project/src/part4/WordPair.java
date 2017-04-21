package part4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author 985565
 */
public class WordPair implements WritableComparable<WordPair> {

	private Text word;
	private Text neighbor;

	public WordPair() {
		word = new Text();
		neighbor = new Text();
	}
	
	public WordPair(String word, String neigbor) {
		this.word = new Text(word);
		this.neighbor = new Text(neigbor);
	}

	public Text getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word.set(word);;
	}

	public Text getNeighbor() {
		return neighbor;
	}

	public void setNeighbor(String neighbor) {
		this.neighbor.set(neighbor);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		word.readFields(in);
		neighbor.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		word.write(out);
		neighbor.write(out);
	}

	@Override
	public int compareTo(WordPair other) {
		int i = this.word.compareTo(other.getWord());
		if (0 == i)
			i = this.neighbor.compareTo(other.getNeighbor());		
		return i;
	}

	@Override
	public int hashCode() {
		return 31 * word.hashCode() + 67 * neighbor.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		boolean isEqual = false;
		if (o instanceof WordPair) {
			WordPair p = (WordPair) o;
			isEqual = word.equals(p.word) && neighbor.equals(p.neighbor);
		}
		return isEqual;
	}

	@Override
	public String toString() {
		return "(" + word + ", " + neighbor + ")";
	}

}
