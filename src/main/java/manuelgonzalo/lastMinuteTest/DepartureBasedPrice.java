package manuelgonzalo.lastMinuteTest;

/**
 * Created by manuel on 27/4/17.
 */
public class DepartureBasedPrice {

    private Integer from;
    private Integer to;
    private Float percentagePrice;

    public DepartureBasedPrice(Integer from, Integer to, Float percentagePrice) {
        this.from = from;
        this.to = to;
        this.percentagePrice = percentagePrice;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Float getPercentagePrice() {
        return percentagePrice;
    }

    public void setPercentagePrice(Float percentagePrice) {
        this.percentagePrice = percentagePrice;
    }
}
