package part4;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import parts.*;

public class HybridReducer extends Reducer<WordPair, IntWritable, Text, Stripe> {
	private HashMap<String, Integer> H;
	private double total;
	private String prev;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		super.setup(context);
		H = new HashMap<String, Integer>();
		prev = null;
		total = 0;
	}

	@Override
	protected void reduce(WordPair pair, Iterable<IntWritable> counts, Context context)	throws IOException, InterruptedException {
		String w = pair.getWord().toString();
		String u = pair.getNeighbor().toString();
		if (prev != null && !prev.equals(w)) {
			context.write(new Text(prev), getUpdatedStripe());
			H = new HashMap<String, Integer>();
			total = 0;
		}
		int sum = sum(counts);
		total += sum;
		H.put(u, sum);
		prev = w;
	}

	@Override
	protected void cleanup(Context context) throws IOException,	InterruptedException {
		super.cleanup(context);
		context.write(new Text(prev), getUpdatedStripe());
	}
	private Stripe getUpdatedStripe() {
		Stripe stripe = new Stripe();
		DecimalFormat df = new DecimalFormat("#.###");
		double frequency = 0.0;
		for (Entry<String, Integer> entry : H.entrySet()) {
			frequency = entry.getValue() / total;
			stripe.put(new Text(entry.getKey()),
					new DoubleWritable(Double.valueOf(df.format(frequency))));
		}
		return stripe;
	}
	private int sum(Iterable<IntWritable> values) {
		int sum = 0;
		for (IntWritable intWritable : values) {
			sum += intWritable.get();
		}
		return sum;
	}
}