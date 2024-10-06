package ua.edu.ucu.apps.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private static final double ABSOLUTE_ZERO = -273.0;
    private double[] temperatures;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.temperatures = new double[0];
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatures) {
        for (double temp : temperatures) {
            if (temp < ABSOLUTE_ZERO) {
                throw new InputMismatchException("Temperature below -273");
            }
        }
        this.temperatures = temperatures.clone();
        this.size = temperatures.length;
    }

    private void checkIfEmpty() {
        if (size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
    }

    public double average() {
        checkIfEmpty();
        double sum = 0;
        for (double temp : temperatures) {
            sum += temp;
        }
        return sum / size;
    }

    public double deviation() {
        checkIfEmpty();
        double mean = average();
        double sum = 0;
        for (double temp : temperatures) {
            sum += (temp - mean) * (temp - mean);
        }
        return Math.sqrt(sum / size);
    }

    public double min() {
        checkIfEmpty();
        double minTemp = temperatures[0];
        for (double temp : temperatures) {
            if (temp < minTemp) {
                minTemp = temp;
            }
        }
        return minTemp;
    }

    public double max() {
        checkIfEmpty();
        double maxTemp = temperatures[0];
        for (double temp : temperatures) {
            if (temp > maxTemp) {
                maxTemp = temp;
            }
        }
        return maxTemp;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0.0);
    }

    public double findTempClosestToValue(double tempValue) {
        checkIfEmpty();
        double closestTemp = temperatures[0];
        for (double temp : temperatures) {
            if (Math.abs(temp - tempValue) < Math.abs(closestTemp - tempValue)) {
                closestTemp = temp;
            } else if (Math.abs(temp - tempValue) == Math.abs(closestTemp - tempValue) && temp > closestTemp) {
                closestTemp = temp;
            }
        }
        return closestTemp;
    }

    public double[] findTempsLessThan(double tempValue) {
        int count = 0;
        for (double temp : temperatures) {
            if (temp < tempValue) {
                count++;
            }
        }
        double[] result = new double[count];
        int index = 0;
        for (double temp : temperatures) {
            if (temp < tempValue) {
                result[index++] = temp;
            }
        }
        return result;
    }

    public double[] findTempsGreaterThan(double tempValue) {
        int count = 0;
        for (double temp : temperatures) {
            if (temp >= tempValue) {
                count++;
            }
        }
        double[] result = new double[count];
        int index = 0;
        for (double temp : temperatures) {
            if (temp >= tempValue) {
                result[index++] = temp;
            }
        }
        return result;
    }

    public double[] findTempsInRange(double lowerBound, double upperBound) {
        int count = 0;
        for (double temp : temperatures) {
            if (temp >= lowerBound && temp <= upperBound) {
                count++;
            }
        }
        double[] result = new double[count];
        int index = 0;
        for (double temp : temperatures) {
            if (temp >= lowerBound && temp <= upperBound) {
                result[index++] = temp;
            }
        }
        return result;
    }

    public void reset() {
        temperatures = new double[0];
        size = 0;
    }

    public double[] sortTemps() {
        double[] sortedTemps = temperatures.clone();
        java.util.Arrays.sort(sortedTemps);
        return sortedTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkIfEmpty();
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (double temp : temps) {
            if (temp < ABSOLUTE_ZERO) {
                throw new InputMismatchException("Temperature below -273");
            }
        }
        if (size + temps.length > temperatures.length) {
            double[] newTemps = new double[(size) * 2];
            System.arraycopy(temperatures, 0, newTemps, 0, size);
            temperatures = newTemps;
        }
        System.arraycopy(temps, 0, temperatures, size, temps.length);
        size += temps.length;
        return size;
    }
}
