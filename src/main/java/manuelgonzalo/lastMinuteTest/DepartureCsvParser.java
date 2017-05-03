package manuelgonzalo.lastMinuteTest;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by manuel on 27/4/17.
 */
public class DepartureCsvParser {

    private static final String SPLIT_CHAR = ",";

    public static NavigableMap<Integer, DepartureBasedPrice> parseFile(String filename) {
        BufferedReader br = null;
        NavigableMap<Integer, DepartureBasedPrice> map = new TreeMap<Integer, DepartureBasedPrice>();

        ClassLoader classLoader = DepartureCsvParser.class.getClassLoader();
        URL url = classLoader.getResource(filename);

        try {
            InputStream is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = br.readLine();
            while (line != null) {
                parseLine(map, line);
                line = br.readLine();
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("DepartureCsvParser - File not found: " + filename);
        } catch (IOException ioe) {
            System.out.println("DepartureCsvParser - IO Exception: " + ioe.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException ioe) {
                System.out.println("DepartureCsvParser - Error closing file: " + ioe.getMessage());
            }
        }
        return map;
    }

    private static void parseLine(NavigableMap<Integer, DepartureBasedPrice> map, String line) {
        String[] lineParts = line.split(SPLIT_CHAR);
        if (lineParts.length != 3) {
            System.out.println("DepartureCsvParser - Strange line: " + line);
            return;
        }
        String fromStr = lineParts[0];
        String toStr = lineParts[1];
        String percentageStr = lineParts[2];

        try {
            Integer from = 0;
            if (!fromStr.isEmpty()) {
                from = Integer.parseInt(fromStr);
            }
            Integer to = Integer.MAX_VALUE;
            if (!toStr.isEmpty()) {
                to = Integer.parseInt(toStr);
            }
            Float percentage = Float.valueOf(percentageStr);

            DepartureBasedPrice departureBasedPrice = new DepartureBasedPrice(from, to, percentage);
            map.put(from, departureBasedPrice);

        } catch (NumberFormatException nfe) {
            System.out.println("DepartureCsvParser - Wrong number in line: " + line);
        }
    }

    public static void main(String[] args) {
        Map<Integer, DepartureBasedPrice> map = parseFile("departurePrices.csv");
        System.exit(0);
    }
}

