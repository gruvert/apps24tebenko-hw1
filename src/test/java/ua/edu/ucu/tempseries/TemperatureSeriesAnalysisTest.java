package ua.edu.ucu.tempseries;

import ua.edu.ucu.apps.tempseries.TempSummaryStatistics;
import ua.edu.ucu.apps.tempseries.TemperatureSeriesAnalysis;

import java.util.InputMismatchException;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TemperatureSeriesAnalysisTest {

    @Test
    public void testAverageWithSingleValue() {
        double[] temps = {1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(1.0, seriesAnalysis.average(), 0.00001);
    }

    @Test
    public void testDeviationWithTwoValues() {
        double[] temps = {1.0, -1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(1.0, seriesAnalysis.deviation(), 0.00001);
    }

    @Test
    public void testMinTemperature() {
        double[] temps = {2.0, 3.0, -1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(-1.0, seriesAnalysis.min(), 0.00001);
    }

    @Test
    public void testMaxTemperature() {
        double[] temps = {2.0, 3.0, -1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(5.0, seriesAnalysis.max(), 0.00001);
    }

    @Test
    public void testFindTempClosestToZero() {
        double[] temps = {1.0, -0.5, 0.5, 0.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(0.0, seriesAnalysis.findTempClosestToZero(), 0.00001);
    }

    @Test
    public void testFindTempClosestToValuePositiveBias() {
        double[] temps = {1.0, -0.2, 0.2};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(0.2, seriesAnalysis.findTempClosestToZero(), 0.00001);
    }

    @Test
    public void testFindTempClosestToValue() {
        double[] temps = {1.0, -2.0, 3.0, 4.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(3.0, seriesAnalysis.findTempClosestToValue(2.5), 0.00001);
    }

    @Test
    public void testFindTempsLessThan() {
        double[] temps = {1.0, -1.0, 2.0, 3.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        double[] result = seriesAnalysis.findTempsLessThan(1.5);
        assertEquals(1.0, result[0], 0.00001);
        assertEquals(-1.0, result[1], 0.00001);
        
    }

    @Test
    public void testFindTempsGreaterThan() {
        double[] temps = {1.0, -1.0, 2.0, 3.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        double[] result = seriesAnalysis.findTempsGreaterThan(1.0);
        assertEquals(1.0, result[0], 0.00001);
        assertEquals(2.0, result[1], 0.00001);
        assertEquals(3.0, result[2], 0.00001);
    }

    @Test
    public void testFindTempsInRange() {
        double[] temps = {1.0, -1.0, 2.0, 3.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        double[] result = seriesAnalysis.findTempsInRange(1.0, 3.0);
        assertEquals(2.0, result[1],0.00001);
    }

    @Test
    public void testReset() {
        double[] temps = {1.0, -1.0, 2.0, 3.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        seriesAnalysis.reset();
        assertThrows(IllegalArgumentException.class, seriesAnalysis::average);
    }

    @Test
    public void testSortTemps() {
        double[] temps = {3.0, 1.0, 2.0, -1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        double[] result = seriesAnalysis.sortTemps();
        assertEquals(-1.0, result[0], 0.00001);
        assertEquals(1.0, result[1], 0.00001);
        assertEquals(2.0, result[2], 0.00001);
        assertEquals(3.0, result[3], 0.00001);
    }

    @Test
    public void testAddTempsWithResize() {
        double[] temps = {1.0, 2.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        int newSize = seriesAnalysis.addTemps(3.0, 4.0);
        assertEquals(4, newSize);
        assertEquals(1.0, seriesAnalysis.sortTemps()[0], 0.00001);
        assertEquals(2.0, seriesAnalysis.sortTemps()[1], 0.00001);
        assertEquals(3.0, seriesAnalysis.sortTemps()[2], 0.00001);
        assertEquals(4.0, seriesAnalysis.sortTemps()[3], 0.00001);
    }

    @Test
    public void testAddTempsThrowsInputMismatchException() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(InputMismatchException.class, () -> seriesAnalysis.addTemps(3.0, -300.0));
    }

    @Test
    public void testSummaryStatistics() {
        double[] temps = {1.0, 2.0, 3.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temps);
        TempSummaryStatistics stats = seriesAnalysis.summaryStatistics();
        assertEquals(2.0, stats.getAvgTemp(), 0.00001);
        assertEquals(0.816496580927726, stats.getDevTemp(), 0.00001);
        assertEquals(1.0, stats.getMinTemp(), 0.00001);
        assertEquals(3.0, stats.getMaxTemp(), 0.00001);
    }

    @Test
    public void testSummaryStatisticsEmptySeries() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(IllegalArgumentException.class, seriesAnalysis::summaryStatistics);
    }

    @Test
    public void testAverageThrowsExceptionForEmptySeries() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(IllegalArgumentException.class, seriesAnalysis::average);
    }

    @Test
    public void testDeviationThrowsExceptionForEmptySeries() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(IllegalArgumentException.class, seriesAnalysis::deviation);
    }

    @Test
    public void testMinThrowsExceptionForEmptySeries() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(IllegalArgumentException.class, seriesAnalysis::min);
    }

    @Test
    public void testMaxThrowsExceptionForEmptySeries() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(IllegalArgumentException.class, seriesAnalysis::max);
    }

    @Test
    public void testFindTempClosestToZeroThrowsExceptionForEmptySeries() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(IllegalArgumentException.class, seriesAnalysis::findTempClosestToZero);
    }

    @Test
    public void testFindTempClosestToValueThrowsExceptionForEmptySeries() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        assertThrows(IllegalArgumentException.class, () -> seriesAnalysis.findTempClosestToValue(5.0));
    }
}