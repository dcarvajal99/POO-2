package com.grupo7.ecommerce.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class CurrencyUtils {
    private static final Locale LOCALE_CL = new Locale("es", "CL");

    private CurrencyUtils() {}

    public static String formatCL(double amount) {
        return NumberFormat.getCurrencyInstance(LOCALE_CL).format(amount);
    }

    public static String formatAhorroCL(double original, double finalPrice) {
        double ahorro = Math.max(0, original - finalPrice);
        return formatCL(ahorro);
    }
}

