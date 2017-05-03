package manuelgonzalo.lastMinuteTest;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by manuel on 27/4/17.
 */
public class PriceCalculator {

    DecimalFormat df = new DecimalFormat("0.##");
    Map<String, Map<String, List<FlightModel>>> flightsPrices;
    Map<String, Float> infantPrices;
    NavigableMap<Integer, DepartureBasedPrice> departurePercentagePrices;


    public PriceCalculator() {
        flightsPrices = FlightsCsvParser.parseFile("flights.csv");
        infantPrices = InfantsCsvParser.parseFile("infantPrices.csv");
        departurePercentagePrices = DepartureCsvParser.parseFile("departurePrices.csv");
    }

    public List<String> calculatePrice(String origin, String destination, int daysToDeparture, int adultsNo, int childrenNo, int infantsNo) {
        List<FlightModel> normalPricesList = getNormalPrices(origin, destination);
        if (normalPricesList.isEmpty()) {
            /// No route
            System.out.println("no flights available - no route");
            return Collections.emptyList();
        }
        Float departureDatePercentage = getDepartureDatePercentage(daysToDeparture);
        if (departureDatePercentage == null) {
            /// No percentage price defined for these days
            System.out.println("no flights available - date doesn't fall into any predefined period");
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<String>();
        for (FlightModel flightModel : normalPricesList) {
            Float finalPrice = getFinalPrice(flightModel, departureDatePercentage, adultsNo, childrenNo, infantsNo);
            if (finalPrice == null) {
                /// The company doesn't have prices for infants
                System.out.println("no flights available - company doesn't have prices for infants");
                continue;
            }
            result.add("* " + flightModel.getAirlineFlight() + ", " + df.format(finalPrice) + " €");
            System.out.println("Flight: " + flightModel.getAirlineFlight() + ", price: " + df.format(finalPrice) + " €");
        }
        return result;
    }

    private List<FlightModel> getNormalPrices(String origin, String destination) {
        Map<String, List<FlightModel>> originMap = flightsPrices.get(origin);
        if (originMap != null) {
            List<FlightModel> destinationList = originMap.get(destination);
            if (destinationList != null) {
                return destinationList;
            }
        }
        return Collections.emptyList();
    }

    private Float getDepartureDatePercentage(int daysToDeparture) {
        Map.Entry<Integer, DepartureBasedPrice> entry = departurePercentagePrices.floorEntry(daysToDeparture);
        if (entry == null) {
            return null;
        } else if (daysToDeparture <= entry.getValue().getTo()) {
            return entry.getValue().getPercentagePrice();
        } else {
            return null;
        }
    }

    private Float getFinalPrice(FlightModel flightModel, Float departureDatePercentage, int adultsNo, int childrenNo, int infantsNo) {
        Float finalPrice = 0f;
        if (adultsNo > 0) {
            finalPrice += adultsNo * flightModel.getBasePrice() * departureDatePercentage / 100;
        }
        if (childrenNo > 0) {
            finalPrice += childrenNo * flightModel.getBasePrice() * departureDatePercentage / 100 * 0.67f;
        }
        if (infantsNo > 0) {
            String iataCode = flightModel.getIataCode();
            Float infantPrice = infantPrices.get(iataCode);
            if (infantPrice == null) {
                return null;
            }
            finalPrice += infantsNo * infantPrice;
        }
        return finalPrice;
    }

    public static void main(String[] args) {
        PriceCalculator priceCalculator = new PriceCalculator();
        priceCalculator.calculatePrice("AMS", "FRA", 31, 1, 0, 0);
        priceCalculator.calculatePrice("LHR", "IST", 15, 2, 1, 1);
        priceCalculator.calculatePrice("BCN", "MAD", 2, 1, 2, 0);
        priceCalculator.calculatePrice("CDG", "FRA", 15, 2, 1, 0);
        System.exit(0);
    }
}
