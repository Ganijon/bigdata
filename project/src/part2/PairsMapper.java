package part2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PairsMapper extends Mapper<LongWritable, Text, Pair, IntWritable> {

    private final IntWritable one = new IntWritable(1);

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        List<String> terms = Arrays.asList(line.split("\\s"));

        int index = 0;
        for (String w : terms) {
            for (String u : getNeighbors(terms, index)) {
                context.write(new Pair(w,u), one);
            }
            index++;
        }
    }

    private List<String> getNeighbors(List<String> words, int index) {
        List<String> list = new ArrayList<>();
        for (int i = index + 1; i < words.size(); i++) {
            if (words.get(index).equals(words.get(i))) {
                break;
            }
            list.add(words.get(i));
        }
        return list;
    }
}
