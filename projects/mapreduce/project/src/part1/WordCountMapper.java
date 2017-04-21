package part1;

/**
 *
 * @author ganijon
 */

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String input = value.toString();
		String[] readLines = input.split("//.*\n");

		for (String line : readLines) {
			String[] words = line.split("\\s");
			for (String word : words) {
				context.write(new Text(word), new IntWritable(1));
			}
		}
	}
}
