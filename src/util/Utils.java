package util;

import java.io.BufferedReader;
import java.io.StringReader;

public class Utils {
    public static BufferedReader GetBufferedReader(String source) {
        return new BufferedReader(new StringReader(source));
    }
}
