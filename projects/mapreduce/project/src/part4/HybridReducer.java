package part4;

/**
 *
 * @author ganijon
 */

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import parts.WordPair;

public class HybridReducer extends MapReduceBase implements
		Reducer<WordPair, IntWritable, Text, Text> {

	private Text wPrev = null;
	private int total = 0;
	private HashMap<String, Double> stripes = new HashMap<String, Double>();

	@Override
	public void reduce(WordPair key, Iterator<IntWritable> values,
			OutputCollector<Text, Text> output, Reporter r) throws IOException {

		Text w = key.getWord();
		Text u = key.getNeighbor();

		if (wPrev == null)
			wPrev = w;
		else if (!wPrev.equals(w)) {
			updateStripes(stripes, total);
			System.out.println(wPrev + ", " + stripesToText(stripes));
			output.collect(wPrev, stripesToText(stripes));
			stripes.clear();
			total = 0;

		}
		int sum = sum(values);
		total += sum;
		stripes.put(u.toString(), (double) sum);
		wPrev = w;
	}

	private int sum(Iterator<IntWritable> values) {
		int sum = 0;
		while (values.hasNext())
			sum += values.next().get();
		return sum;
	}

	private void updateStripes(HashMap<String, Double> stripes, int total) {
		for (Entry<String, Double> entry : stripes.entrySet()) {
			double newVal = entry.getValue();
			double frequency = newVal / total;
			stripes.put(entry.getKey(), frequency);
		}
	}

	private Text stripesToText(HashMap<String, Double> result) {
		StringBuilder stripes = new StringBuilder("{ ");
		DecimalFormat df = new DecimalFormat("#.##");
		for (Entry<String, Double> e : result.entrySet())
			stripes.append("{" + e.getKey() + ":" + df.format(e.getValue())
					+ "},");
		stripes.append(" }");
		return new Text(stripes.toString());
	}

}
