package lab3.task1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import lab2.task1.Mapper;
import lab2.task1.Pair;
import lab2.task1.Reducer;

/**
 *
 * @author Ganijon
 */
public class WordCount {

    private Mapper[] mappers;
    private Reducer[] reducers;

    public void init() {
        Scanner in = new Scanner(System.in);

        System.out.print("Number of Input-Splits: ");
        int nInputSplits = tryParse(in.nextLine(), 3);

        System.out.print("Number of Reducers: ");
        int nReducers = tryParse(in.nextLine(), 3);

        mappers = new Mapper[nInputSplits];
        reducers = new Reducer[nReducers];
    }

    public Map<Integer, List<Pair>> map() {

        String[] inputSplits = new String[mappers.length];
        for (int i = 0; i < inputSplits.length; i++) {
            inputSplits[i] = getInputSplit("/lab3/data" + i + ".txt");
            System.out.printf("Mapper %d Input:\n%s", i, inputSplits[i]);
        }

        Map<Integer, List<Pair>> mapOut = new HashMap<>();
        for (int i = 0; i < mappers.length; i++) {
            mappers[i] = new Mapper();
            List<Pair> pairs = mappers[i].map(inputSplits[i]);
            System.out.printf("Mapper %d Output: \n", i);
            pairs.stream().forEach(System.out::println);
            mapOut.put(i, pairs);
        }
        return mapOut;
    }

    public void partition(Map<Integer, List<Pair>> mapOut) {
        Map<Integer, List<Pair>> reducerIn = new HashMap();
        
        for(int mapperId: mapOut.keySet()) {
             
             for(Pair pair: mapOut.get(mapperId)) {
                 
                 int reducerId = getPartition(pair.getKey());
                
                 if(reducerId == mapperId) {
                     
                     
                 }
                 else {
                      
                 }
             }
                 
             
         }
        
        for(int i = 0; i < reducers.length; i++) {
            
        }
        //System.out.printf("Pairs send from Mapper %d to Reducer %d\n", mapperId, reducerId);
    }
    public void reduce(List<Pair> mapOut) {
        
    }
    
    private int getPartition(Object key) {
        return (int) key.hashCode() % reducers.length;
    }

    private int tryParse(String n, int fallback) {
        int result;
        try {
            result = Integer.parseInt(n);
        } catch (NumberFormatException e) {
            result = fallback;
        }
        return result;
    }

    private String getInputSplit(String datafile) {
        Scanner scanner = new Scanner(getClass().getResourceAsStream(datafile));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        return sb.toString();
    }
}
