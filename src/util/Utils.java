package util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {
    /**
     * A static instance of the date formatter.
     */
    private static SimpleDateFormat dateFormatter;

    /**
     * Parse a string and extract colon seperated pairs.
     * @param source source string to parse
     * @return List of string pairs
     * @throws Exception if an invalid pair exists in the string
     */
    public static ArrayList<String[]> GetPairs(String source) throws Exception {
        BufferedReader br = new BufferedReader(new StringReader(source));
        String line;
        ArrayList<String[]> pairs = new ArrayList<>();
        while ( (line = br.readLine()) != null ) {
            String[] pair = line.split(":");
            if (pair.length != 2) throw new Exception(String.format("Invalid line: %s", line));
            pairs.add(pair);
        }

        return pairs;
    }

    /**
     * Gets an instance of the date formatter
     * @return instance of a date formatter
     */
    public static SimpleDateFormat getDateFormatter() {
        if (Utils.dateFormatter == null) {
            Utils.dateFormatter = new SimpleDateFormat("dd-M-yyyy HH:mm:ss", Locale.ENGLISH);
            Utils.dateFormatter.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
        }
        return Utils.dateFormatter;
    }

    /**
     * Formats the time difference between two date objects in a human friendly format.
     * Inspired by a solution suggested by Sebastian Lorber on StackOverFlow
     * https://stackoverflow.com/a/10650881/7799568
     * @param end Date object for the ending of a duration
     * @param start Date object for the start of a duration
     * @return Human friendly string for the time difference
     */
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
