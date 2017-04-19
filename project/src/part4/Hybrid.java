package part4;

/**
 *
 * @author 985565
 */

import java.util.HashMap;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
 
public class Hybrid {
	
    public static void main(String args[]) throws Exception
    {
        //Check the correctness of the entered command
        if(args.length != 2)
        {
            System.err.println("Inavlid Command!");
            System.err.println("Usage: Pairs <input path> <output path>");
            System.exit(0);
        }
        
        //Instantiate the job object for configuring your job
        Job job = new Job();
        
        //Specify the class that hadoop needs to look in the JAR file
        //This Jar file is then sent to all the machines in the cluster
        job.setJarByClass(Hybrid.class);
        
        //Set a meaningful name to the job
        job.setJobName("Compute Relative Frequency - Hybrid Approach");
        
        //Add the apth from where the file input is to be taken
        FileInputFormat.addInputPath(job, new Path(args[0]));
        
        //Set the path where the output must be stored
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        //Set the Mapper and the Reducer class
        job.setMapperClass(HybridMapper.class);
        job.setReducerClass(HybridReducer.class);
        
        //Set the type of the key and value of Mapper and Reducer
        /*
         * If the Mapper output type and Reducer output type are not the same then
         * also include setMapOutputKeyClass() and setMapOutputKeyValue()
         */
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(HashMap.class);
        
        
        job.setMapOutputKeyClass(Pair.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        //Start the job and wait for it to finish. And exit the program based on
        //the success of the program
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
