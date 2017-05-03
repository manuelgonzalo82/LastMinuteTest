/**
 * Created by manuel on 28/4/17.
 */
import manuelgonzalo.lastMinuteTest.PriceCalculator;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;


public class Test1 {

    @Test
    public void test1() {
        PriceCalculator priceCalculator = new PriceCalculator();
        String[] expected1Array = {"* TK2372, 157,6 €", "* TK2659, 198,4 €", "* LH5909, 90,4 €"};
        //List<String> expected1 = Arrays.asList("* TK2372, 157,6 €", "* TK2659, 198,4 €", "* LH5909, 90,4 €");
        List<String> result1 = priceCalculator.calculatePrice("AMS", "FRA", 31, 1, 0, 0);
        for (String res : result1) {
            System.out.println("res: " + res);
        }
        assertThat(result1, hasSize(3));
        assertThat(result1, containsInAnyOrder(expected1Array));
    }

    @Test
    public void test2() {
        PriceCalculator priceCalculator = new PriceCalculator();

        String[] expected2Array = {"* TK8891, 806 €", "* LH1085, 481,19 €"};
        //List<String> expected2 = Arrays.asList("* TK8891, 806 €", "* LH1085, 481,19 €");
        List<String> result2 = priceCalculator.calculatePrice("LHR", "IST", 15, 2, 1, 1);
        assertThat(result2, hasSize(2));
        assertThat(result2, containsInAnyOrder(expected2Array));
    }

    @Test
    public void test3() {
        PriceCalculator priceCalculator = new PriceCalculator();

        String[] expected3Array = {"* IB2171, 909,09 €", "* LH5496, 1028,43 €"};
        //List<String> expected3 = Arrays.asList("* IB2171, 909,09 €", "* LH5496, 1028,43 €");
        List<String> result3 = priceCalculator.calculatePrice("BCN", "MAD", 2, 1, 2, 0);
        assertThat(result3, hasSize(2));
        assertThat(result3, containsInAnyOrder(expected3Array));
    }

    @Test
    public void test4() {
        PriceCalculator priceCalculator = new PriceCalculator();

        List<String> result4 = priceCalculator.calculatePrice("CDG", "FRA", 15, 2, 1, 0);
        assertThat(result4, is(IsEmptyCollection.empty()));
    }
}
