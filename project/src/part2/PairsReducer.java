package part2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class PairsReducer extends Reducer<Pair, IntWritable, Pair, IntWritable> {

    private int total = 0;

    @Override
    public void reduce(Pair pair, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        for (IntWritable val : values) {
            sum += val.get();
        }

        if (pair.right.equals(PairsMapper.STAR)) {
            total = sum;
        } else {
            context.write(pair, new IntWritable(sum / total));
        }

    }
}
