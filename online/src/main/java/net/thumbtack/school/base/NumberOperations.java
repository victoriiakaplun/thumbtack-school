package net.thumbtack.school.base;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberOperations {
    public static Integer find(int[] array, int value) {
        for (Integer pos = 0; pos < array.length; pos++) {
            if (array[pos] == value) {
                return pos;
            }
        }
        return null;
    }

    public static Integer find(double[] array, double value, double eps) {
        for (Integer pos = 0; pos < array.length; pos++) {
            if (Math.abs(array[pos] - value) <= eps) {
                return pos;
            }
        }
        return null;
    }

    public static Double calculateDensity(double weight, double volume, double min, double max) {
        Double density = weight / volume;
        return (density < min || density > max) ? null : density;
    }

    public static Integer find(BigInteger[] array, BigInteger value) {
        for (Integer pos = 0; pos < array.length; pos++) {
            if (array[pos].compareTo(value) == 0) {
                return pos;
            }
        }
        return null;
    }

    public static BigDecimal calculateDensity(BigDecimal weight, BigDecimal volume, BigDecimal min, BigDecimal max) {
        BigDecimal density = weight.divide(volume);
        return (density.compareTo(max) == 1 || density.compareTo(min) == -1) ? null : density;
    }
}