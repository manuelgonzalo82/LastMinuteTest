package manuelgonzalo.lastMinuteTest;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manuel on 27/4/17.
 */
public class FlightsCsvParser {

    private static final String SPLIT_CHAR = ",";


    public static Map<String, Map<String, List<FlightModel>>> parseFile(String filename) {
        BufferedReader br = null;
        Map<String, Map<String, List<FlightModel>>> map = new HashMap<String, Map<String, List<FlightModel>>>();

        ClassLoader classLoader = FlightsCsvParser.class.getClassLoader();
        URL url = classLoader.getResource(filename);

        try {
            InputStream is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = br.readLine();
            boolean isFirstLine = true;
            while (line != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    parseLine(map, line);
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("FlightsCsvParser - File not found: " + filename);
        } catch (IOException ioe) {
            System.out.println("FlightsCsvParser - IO Exception: " + ioe.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException ioe) {
                System.out.println("FlightsCsvParser - Error closing file: " + ioe.getMessage());
            }
        }
        return map;
    }

    private static void parseLine(Map<String, Map<String, List<FlightModel>>> map, String line) {
        String[] lineParts = line.split(SPLIT_CHAR);
        if (lineParts.length != 4) {
            System.out.println("FlightsCsvParser - Strange line: " + line);
            return;
        }
        String origin = lineParts[0].trim();
        String destination = lineParts[1].trim();
        String airlineFlight = lineParts[2].trim();
        Float basePrice = 0f;
        try {
            basePrice = Float.valueOf(lineParts[3]);
        } catch (NumberFormatException nfe) {
            System.out.println("FlightsCsvParser - Wrong base price: " + lineParts[3]);
            return;
        }

        FlightModel flightModel = new FlightModel(origin, destination, airlineFlight, basePrice);
        Map<String, List<FlightModel>> originMap = map.get(origin);
        if (originMap == null) {
            originMap = new HashMap<String, List<FlightModel>>();
            map.put(origin, originMap);
        }
        List<FlightModel> destinationList = originMap.get(destination);
        if (destinationList == null) {
            destinationList = new ArrayList<FlightModel>();
            originMap.put(destination, destinationList);
        }
        destinationList.add(flightModel);
    }

    public static void main(String[] args) {
        Map<String, Map<String, List<FlightModel>>> map = parseFile("flights.csv");
        System.exit(0);
    }

}
