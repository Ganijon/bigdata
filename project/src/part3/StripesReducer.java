package part3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StripesReducer extends Reducer<Text, Map<String, Integer>, Text, Map<String, Integer>> {

    @Override
    public void reduce(Text w, Iterable<Map<String, Integer>> stripes, Context context) throws IOException, InterruptedException {

        Map<String, Integer> Hf = new HashMap<>();

        for(Map<String, Integer> H: stripes) {
         
            H.keySet().forEach((u) -> {
                int newValue = 0;
                
                if(Hf.containsKey(u)) {
                    newValue = H.get(u) + Hf.get(u);
                }
                
                Hf.put(u, newValue);
            });
        }
        
         context.write(w, Hf);
    }
}
