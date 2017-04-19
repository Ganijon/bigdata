package part3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StripesMapper extends Mapper<LongWritable, Text, Text, Map<String, Integer>> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        List<String> terms = Arrays.asList(line.split("\\s"));

        int uIndex = 0;

        for (String w : terms) {

            Map<String, Integer> H = new HashMap<>();

            for (String u : getNeighbors(terms, uIndex)) {

                int count = 1;
                if (H.containsKey(u)) {
                    count = H.get(u);
                    count++;
                }
                H.put(u, count);
            }
            uIndex++;

            context.write(new Text(w), H);
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
