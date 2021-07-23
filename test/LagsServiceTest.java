import com.opencsv.CSVWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.ArrayList;

public class LagsServiceTest {
    private ArrayList<Order> orders;
    private LagsService lagsService;
    @Before
    public void setUp() {
        lagsService = new LagsService();
        orders = lagsService.getOrders();
    }
    @Test
    public void givenNoOrdersTheIncomeIsZero() {
        lagsService.computeIncome(true);
        Assert.assertEquals(0, lagsService.getIncome(), 0.00001);
    }
    @Test
    public void givenOneOrderTheIncomeIsThePriceOfThatOrder() {
        orders.add(new Order("FOO", 0, 5, 100.0));
        lagsService.computeIncome(true);
        Assert.assertEquals(100.0, lagsService.getIncome(), 0.00001);
    }
    // 0, 1, N
    @Test
    public void givenTwoCompatibleOrdersTheIncomeIsTheSumOfThePrices() {
        orders.add(new Order("FOO", 0, 5, 100.0));
        orders.add(new Order("QUX", 5, 9, 70.0));
        lagsService.computeIncome(true);
        Assert.assertEquals(170.0, lagsService.getIncome(), 0.00001);
    }
    @Test
    public void givenTwoIncompatibleOrdersTheIncomeIsTheMaximumOfThePrices() {
        orders.add(new Order("FOO", 0, 5, 100.0));
        orders.add(new Order("BAR", 3, 14, 140.0));
        lagsService.computeIncome(true);
        Assert.assertEquals(140.0, lagsService.getIncome(), 0.00001);
    }
    @Test
    public void givenSeveralCompatibleAndIncompatibleOrdersTheIncomeIsTheBestPossible() {
        orders.add(new Order("FOO", 0, 5, 100.0));
        orders.add(new Order("BAR", 3, 14, 140.0));
        orders.add(new Order("QUX", 5, 9, 70.0));
        orders.add(new Order("QUZ", 6, 9, 80.0));
        lagsService.computeIncome(true);
        Assert.assertEquals(180.0, lagsService.getIncome(), 0.00001);
    }
    @Test
    public void givenSeveralOrdersSavingWillCreateACSVOfOrders() {
        orders.add(new Order("FOO", 0, 5, 100.0));
        orders.add(new Order("BAR", 3, 14, 140.0));
        orders.add(new Order("QUX", 5, 9, 70.0));
        orders.add(new Order("QUZ", 6, 9, 80.0));
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        lagsService.saveCSV(csvWriter);
        String csvContent = "id;start;duration;price\nFOO;0;5;100.0\nBAR;3;14;140.0\nQUX;5;9;70.0\nQUZ;6;9;80.0\n";
        Assert.assertEquals(csvContent, stringWriter.toString());
    }
}
