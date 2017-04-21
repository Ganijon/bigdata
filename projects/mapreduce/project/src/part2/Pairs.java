package part2;

/**
 *
 * @author 985565
 */

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Pairs {

	public static void main(String args[]) throws Exception {

		if (args.length != 2) {
			System.err.println("Inavlid Command!");
			System.err.println("Usage: Pairs <input path> <output path>");
			System.exit(0);
		}

		Job job = new Job();
		
		job.setJarByClass(Pairs.class);

		job.setJobName("Compute Relative Frequency - Pairs Approach");

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(PairsMapper.class);
		job.setReducerClass(PairsReducer.class);


		job.setOutputKeyClass(WordPair.class);
		job.setOutputValueClass(Text.class);

		job.setMapOutputKeyClass(WordPair.class);
		job.setMapOutputValueClass(IntWritable.class);
		

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
