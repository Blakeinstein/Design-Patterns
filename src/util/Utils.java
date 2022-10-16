package util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public static String FormatTimeDifference(Date end, Date start) {
        // code block inspired from https://stackoverflow.com/a/10650881/7799568, credits to Sebastian Lorber.
        var ms = end.getTime() - start.getTime();
        var units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        var unitDifferences = new HashMap<TimeUnit,Long>();
        var msDiff = ms;

        for ( var unit : units ) {
            var difference = unit.convert(msDiff,TimeUnit.MILLISECONDS);
            msDiff -= unit.toMillis(difference);

            unitDifferences.put(unit,difference);
        }

        return String.format(
                "%s days, %s hours and %s minutes",
                unitDifferences.get(TimeUnit.DAYS),
                unitDifferences.get(TimeUnit.HOURS),
                unitDifferences.get(TimeUnit.MINUTES)
        );
    }
}
