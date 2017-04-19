package part4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HybridReducer extends Reducer<Pair, IntWritable, Text, Map<String, Integer>> {

    private Map<String, Integer> H = new HashMap<>();
    private String wPrev = null;

    @Override
    public void reduce(Pair pair, Iterable<IntWritable> counts, Context context) throws IOException, InterruptedException {
        String w = pair.left, u = pair.right;

        if (!w.equals(wPrev) && wPrev != null) {
            // add all values in H 
            int total = 0;
            for (String key : H.keySet()) {
                total = total + H.get(key);
            }
            // element-wise division
            for (String key : H.keySet()) {
                H.put(key, H.get(key) / total);
            }
            // emit
            context.write(new Text(w), H);
            // reset H
            H = new HashMap<>();

        } else {
            // sum all counts
            int sum = 0;
            for (IntWritable c : counts) {
                sum = sum + c.get();
            }
            // save sum in H
            H.put(u, sum);

            wPrev = w;
        }
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {

        // add all values in H 
        int total = 0;
        for (String key : H.keySet()) {
            total = total + H.get(key);
        }
        // element-wise division
        for (String key : H.keySet()) {
            H.put(key, H.get(key) / total);
        }
        // emit
        context.write(new Text(wPrev), H);
    }

}
