package util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    private static SimpleDateFormat dateFormatter;

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

    public static SimpleDateFormat getDateFormatter() {
        if (Utils.dateFormatter == null) {
            Utils.dateFormatter = new SimpleDateFormat("dd-M-yyyy HH:mm:ss", Locale.ENGLISH);
            Utils.dateFormatter.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
        }
        return Utils.dateFormatter;
    }
}
