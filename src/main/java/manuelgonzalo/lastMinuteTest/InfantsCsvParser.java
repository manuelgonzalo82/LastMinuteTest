package manuelgonzalo.lastMinuteTest;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manuel on 27/4/17.
 */
public class InfantsCsvParser {

    private static final String SPLIT_CHAR = ",";


    public static Map<String, Float> parseFile(String filename) {
        BufferedReader br = null;
        Map<String, Float> map = new HashMap<String, Float>();

        ClassLoader classLoader = InfantsCsvParser.class.getClassLoader();
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

        } catch (IOException ioe) {

        } finally {
            try {
                br.close();
            } catch (IOException ioe) {
                System.out.println("InfantsCsvParser - Error closing file: " + ioe.getMessage());
            }
        }
        return map;
    }

    private static void parseLine(Map<String, Float> map, String line) {
        String[] lineParts = line.split(SPLIT_CHAR);
        if (lineParts.length != 3) {
            System.out.println("InfantsCsvParser - Strange line: " + line);
            return;
        }
        String iataCode = lineParts[0].trim();
        Float infantPrice = 0f;
        try {
            infantPrice = Float.valueOf(lineParts[2]);
        } catch (NumberFormatException nfe) {
            System.out.println("InfantsCsvParser - Wrong infant price: " + lineParts[2]);
            return;
        }


        map.put(iataCode, infantPrice);
    }

    public static void main(String[] args) {
        Map<String, Float> map = parseFile("infantPrices.csv");
        System.exit(0);
    }


}
