package part4a;

/**
 *
 * @author ganijon
 */

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import parts.Stripe;
import parts.WordPair;

public class HybridReducer extends Reducer<WordPair, IntWritable, Text, Stripe> {
	
	private Stripe stripes;
	private Text prev;
	private int total;


	@Override
	protected void setup(Reducer<WordPair, IntWritable, Text, Stripe>.Context context)
			throws IOException, InterruptedException {
		super.setup(context);
		
		stripes = new Stripe();
		prev = null;
		total = 0;
	}
	
	@Override
	public void reduce(WordPair key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		Text w = key.getWord();
		Text u = key.getNeighbor();

		if (w.equals(prev) && prev != null) {
			updateStripes(stripes, total);
			context.write(prev, stripes);
			stripes.clear();
			total = 0;
		}	
		
		int sum = sum(values);
		total += sum;
		stripes.put(new Text(u.toString()), new DoubleWritable(sum));
		prev = w;
	}

	@Override
	protected void cleanup(Reducer<WordPair, IntWritable, Text, Stripe>.Context context)
			throws IOException, InterruptedException {
		super.cleanup(context);
		
		updateStripes(stripes, total);
		context.write(prev, stripes);
	}
	
	private int sum(Iterable<IntWritable> values) {
		int sum = 0;
		for(IntWritable i: values)
			sum += i.get();
		return sum;
	}

	private void updateStripes(Stripe stripes, int total) {
		for (Writable key : stripes.keySet()) {
			double newVal = ((DoubleWritable)stripes.get(key)).get();
			double frequency = newVal / total;
			stripes.put(key, new DoubleWritable(frequency));
		}
	}

}
