package util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public class Utils {

    public static ArrayList<String[]> GetPairs(String source) throws Exception {
        BufferedReader br = new BufferedReader(new StringReader(source));
        String line;
        ArrayList<String[]> pairs = new ArrayList<>();
        while ( (line = br.readLine()) != null ) {
            String[] pair = line.split(":");
            assert pair.length == 2 : "Invalid line: " + line;
            pairs.add(pair);
        }

        return pairs;
    }
}
