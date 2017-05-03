package manuelgonzalo.lastMinuteTest;

/**
 * Created by manuel on 27/4/17.
 */
public class FlightModel {

    private String origin;
    private String destination;
    private String airlineFlight;
    private String iataCode;
    private Float basePrice;

    public FlightModel(String origin, String destination, String airlineFlight, Float basePrice) {
        this.origin = origin;
        this.destination = destination;
        this.airlineFlight = airlineFlight;
        this.basePrice = basePrice;
        parseIataCode();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAirlineFlight() {
        return airlineFlight;
    }

    public void setAirlineFlight(String airlineFlight) {
        this.airlineFlight = airlineFlight;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public void parseIataCode() {
        iataCode = this.airlineFlight.substring(0, 2);
    }

    public String getIataCode() {
        return iataCode;
    }
}
