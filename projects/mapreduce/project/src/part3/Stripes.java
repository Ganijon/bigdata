package part3;

/**
 *
 * @author ganijon
 */

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Stripes {

    public static void main(String args[]) throws Exception {
        
        if (args.length != 2) {
            System.err.println("Invalid Command!");
            System.err.println("Usage: Pairs <input path> <output path>");
            System.exit(0);
        }

        Job job = new Job();

        job.setJarByClass(Stripes.class);

        job.setJobName("Compute Relative Frequency - Stripes Approach");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(StripesMapper.class);
        job.setReducerClass(StripesReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Stripe.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
