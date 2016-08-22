package com.ind.tired.util;

import java.math.BigDecimal;

import static java.lang.Math.abs;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_EVEN;

public class Maths {
    private static final int SCALE = 8;
    public static final BigDecimal TEN = new BigDecimal(10);
    public static final BigDecimal HUNDRED = new BigDecimal(100);

    public static BigDecimal invert(BigDecimal i) {
        return ONE.divide(i, SCALE, HALF_EVEN);
    }

    public static BigDecimal multiply(BigDecimal multiplicand, BigDecimal multiplier) {
        return scale(multiplicand.multiply(multiplier));
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, SCALE, HALF_EVEN);
    }

    public static BigDecimal d(String s) {
        return d(s, SCALE);
    }

    public static BigDecimal d(String s, int scale) {
        return new BigDecimal(s).setScale(scale, HALF_EVEN);
    }

    public static BigDecimal powerTen(int exponent) {
        if (exponent < 0) {
            return divide(ONE, TEN.pow(abs(exponent)));
        }
        return TEN.pow(exponent);
    }

    public static BigDecimal add(BigDecimal addend, BigDecimal augend) {
        return scale(addend.add(augend));
    }

    public static BigDecimal scale(BigDecimal value) {
        return scale(value, SCALE);
    }

    public static BigDecimal scale(BigDecimal value, int scale) {
        return value.setScale(scale, HALF_EVEN);
    }

    public static boolean isLessThanEqualTo(BigDecimal l, BigDecimal r) {
        return l.compareTo(r) <= 0;
    }

    public static boolean isGreaterThanEqualTo(BigDecimal l, BigDecimal r) {
        return l.compareTo(r) >= 0;
    }

    public static boolean isGreaterThan(BigDecimal l, BigDecimal r) {
        return l.compareTo(r) > 0;
    }

    public static BigDecimal minus(BigDecimal from, BigDecimal amount) {
        return from.subtract(amount);
    }

    public static BigDecimal absolute(BigDecimal d) {
        return d.abs();
    }

    public static BigDecimal percent(BigDecimal d) {
        return d.multiply(HUNDRED);
    }
}
