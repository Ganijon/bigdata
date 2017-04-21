package part3;

/**
 *
 * @author ganijon
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StripesMapper extends
		Mapper<LongWritable, Text, Text, Stripe> {

	private Stripe occurenceMap = new Stripe();

	@Override
	public void map(LongWritable key, Text line, Context context)
			throws IOException, InterruptedException {

		List<String> words = Arrays.asList(line.toString().split("\\s"));

		int wordIndex = 0;

		for (String word : words) {

			occurenceMap.clear();

			for (String neighbor : getNeighbors(words, wordIndex)) {

				IntWritable neighborCount = new IntWritable(1);

				if (occurenceMap.containsKey(neighbor)) {
					neighborCount = (IntWritable) occurenceMap.get(neighbor);
					neighborCount.set(1 + neighborCount.get());
				}
				occurenceMap.put(new Text(neighbor), neighborCount);

			}

			wordIndex++;

			context.write(new Text(word), occurenceMap);
		}
	}

	private List<String> getNeighbors(List<String> words, int wordIndex) {

		List<String> neighbors = new ArrayList<>();

		for (int i = wordIndex + 1; i < words.size(); i++) {
			if (words.get(wordIndex).equals(words.get(i))) {
				break;
			}
			neighbors.add(words.get(i));
		}

		return neighbors;
	}
}
