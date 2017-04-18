package project.part1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;


public class WordCountTest {

	/*
	 * Declare harnesses that let you test a mapper, a reducer, and a mapper and
	 * a reducer working together.
	 */
	MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
	ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
	MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

	/*
	 * Set up the test. This method will be called before every test.
	 */
	@Before
	public void setUp() {

		WordCountMapper mapper = new WordCountMapper();
		mapDriver = new MapDriver<LongWritable, Text, Text, IntWritable>();
		mapDriver.setMapper(mapper);

		WordCountReducer reducer = new WordCountReducer();
		reduceDriver = new ReduceDriver<Text, IntWritable, Text, IntWritable>();
		reduceDriver.setReducer(reducer);
		
		mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	}

	@Test
	public void testMapper() throws IOException
	{
	    mapDriver.withInput(new LongWritable(1), new Text("orange orange apple"));
	    mapDriver.withOutput(new Text("orange"), new IntWritable(1));
	    mapDriver.withOutput(new Text("orange"), new IntWritable(1));
	    mapDriver.withOutput(new Text("apple"), new IntWritable(1));
	    mapDriver.runTest();
	}

	@Test
	public void testReducer() throws IOException
	{
	    List<IntWritable> values = new ArrayList<>();
	    values.add(new IntWritable(1));
	    values.add(new IntWritable(1));
	    reduceDriver.withInput(new Text("orange"), values);
	    reduceDriver.withOutput(new Text("orange"), new IntWritable(2));
	    reduceDriver.runTest();
	}

	@Test
	public void testMapperReducer() throws IOException
	{
	    mapReduceDriver.addInput(new LongWritable(1), new Text("orange orange apple"));
	    mapReduceDriver.addOutput(new Text("apple"), new IntWritable(1));
	    mapReduceDriver.addOutput(new Text("orange"), new IntWritable(2));
	    mapReduceDriver.runTest();
	}
}
