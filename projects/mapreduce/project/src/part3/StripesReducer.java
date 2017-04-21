package part3;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

public class StripesReducer extends Reducer<Text, Stripe, Text, Stripe> {

	@Override
	public void reduce(Text word, Iterable<Stripe> stripes, Context context)
			throws IOException, InterruptedException {

		Stripe stripe = new Stripe();

		for (Stripe s : stripes) {
			mergeStripes(stripe, s);
		}

		divideByTotal(stripe, getTotal(stripe));

		context.write(word, stripe);
	}

	private void mergeStripes(Stripe stripe, Stripe s) {

		for (Writable neighbor : s.keySet()) {
			IntWritable neighborCount = (IntWritable) s.get(neighbor);

			if (stripe.containsKey(neighbor)) {
				IntWritable count = (IntWritable) stripe.get(neighbor);
				neighborCount.set(neighborCount.get() + count.get());
			}
			
			stripe.put(neighbor, neighborCount);
		}
	}

	private void divideByTotal(Stripe stripe, int total) {

		Text newValue = new Text();
		IntWritable oldValue;

		for (Writable key : stripe.keySet()) {
			oldValue = (IntWritable) stripe.get(key);
			newValue.set(oldValue.toString() + "/" + String.valueOf(total));
			stripe.put(key, newValue);
		}
	}

	private int getTotal(Stripe stripe) {
		int total = 0;
		for (Writable key : stripe.keySet())
			total += ((IntWritable) stripe.get(key)).get();
		return total;
	}

}
