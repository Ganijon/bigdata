package part2;

/**
 *
 * @author ganijon
 */

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import parts.WordPair;

public class PairsReducer extends Reducer<WordPair, IntWritable, WordPair, Text> {

	private int total;

	@Override
	protected void setup(Reducer<WordPair, IntWritable, WordPair, Text>.Context context) throws IOException, InterruptedException {
		super.setup(context);
		total = 0;
	}
	
	@Override
	public void reduce(WordPair pair, Iterable<IntWritable> counts, Context context) throws IOException, InterruptedException {
		int sum = sum(counts);
		if (pair.getNeighbor().equals(new Text("*"))) {
			total = sum;
		} else {
			String relativeFrequency = sum + "/" + total;
			context.write(pair, new Text(relativeFrequency));
		}
	}
	
	private int sum(Iterable<IntWritable> values) {
		int sum = 0;
		for (IntWritable val : values) {
			sum += val.get();
		}
		return sum;
	}
}
