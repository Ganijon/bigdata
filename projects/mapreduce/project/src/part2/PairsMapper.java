package part2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PairsMapper extends
		Mapper<LongWritable, Text, WordPair, IntWritable> {

	private final IntWritable one = new IntWritable(1);
	private final WordPair pair = new WordPair();
	

	@Override
	public void map(LongWritable key, Text values, Context context)
			throws IOException, InterruptedException {

		List<String> words = Arrays.asList(values.toString().split("\\s"));

		int wordIndex = 0;

		for (String word : words) {

			pair.setWord(word);
			
			for (String neighbor : getNeighbors(words, wordIndex)) {

				pair.setNeighbor(neighbor);
				context.write(pair, one);

				pair.setNeighbor("*");
				context.write(pair, one);
			}

			wordIndex++;
		}
	}

	private List<String> getNeighbors(List<String> words, int wordIndex) {
		
		List<String> neigbors = new ArrayList<>();
		
		for (int i = wordIndex + 1; i < words.size(); i++) {
			if (words.get(wordIndex).equals(words.get(i))) {
				break;
			}
			neigbors.add(words.get(i));
		}
		return neigbors;
	}
}
