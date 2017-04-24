package part4a;

/**
 *
 * @author ganijon
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import parts.WordPair;

public class HybridMapper extends Mapper<LongWritable, Text, WordPair, IntWritable> {

	private HashMap<WordPair, Integer> outputMap = new HashMap<>();

	@Override
	public void map(LongWritable key, Text values, Context context) throws IOException, InterruptedException {

		String input = values.toString();
		String[] readLines = input.split("//.*\n");

		for (String line : readLines) {
			String[] words = line.split("\\s");
			for (int i = 0; i < words.length - 1; i++) {
				for (int j = i + 1; j < words.length; j++) {
					if (words[i].equals(words[j]))
						break;

					WordPair pair = new WordPair(words[i], words[j]);

					if (outputMap.get(pair) != null)
						outputMap.put(pair, outputMap.get(pair) + 1);
					else
						outputMap.put(pair, new Integer(1));
				}
			}
		}

		for (Entry<WordPair, Integer> mapEntry : outputMap.entrySet()) {
			context.write(mapEntry.getKey(), new IntWritable(mapEntry.getValue()));
		}
	}
}
