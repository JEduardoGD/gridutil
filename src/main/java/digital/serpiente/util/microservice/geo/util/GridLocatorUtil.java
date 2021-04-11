package digital.serpiente.util.microservice.geo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import digital.serpiente.util.microservice.geo.dto.Location;

public abstract class GridLocatorUtil {

    private static final String REGEX_GRIDLOCATOR_1 = "([a-rA-R]{2})";
    private static final String REGEX_GRIDLOCATOR_2 = "([a-rA-R]{2}[0-9]{2})";
    private static final String REGEX_GRIDLOCATOR_3 = "([a-rA-R]{2}[0-9]{2}[a-xA-X]{2})";
    private static final String REGEX_GRIDLOCATOR_4 = "([a-rA-R]{2}[0-9]{2}[a-xA-X]{2}[0-9]{2})";
    private static final String REGEX_GRIDLOCATOR_5 = "([a-rA-R]{2}[0-9]{2}[a-xA-X]{2}[0-9]{2}[a-xA-X]{2})";
    private static final String REGEX_GRIDLOCATOR_L = "^(" + REGEX_GRIDLOCATOR_1 + "|" + REGEX_GRIDLOCATOR_2 + "|"
            + REGEX_GRIDLOCATOR_3 + "|" + REGEX_GRIDLOCATOR_4 + "|" + REGEX_GRIDLOCATOR_5 + ")$";

    private GridLocatorUtil() {
        // NO CALL
    }

    public static double distanceTo(Location startLatLon, Location endLatLon, Units unit) {
        int r = 6371;

        if (unit.equals(Units.M)) {
            r *= 1000;
        }

        BigDecimal hn = degreesToRads(startLatLon.getLat());
        BigDecimal he = degreesToRads(startLatLon.getLon());
        BigDecimal n = degreesToRads(endLatLon.getLat());
        BigDecimal e = degreesToRads(endLatLon.getLon());

        double co = Math.cos(he.subtract(e).doubleValue()) * Math.cos(hn.doubleValue()) * Math.cos(n.doubleValue())
                + Math.sin(hn.doubleValue()) * Math.sin(n.doubleValue());
        double ca = Math.atan(Math.abs(Math.sqrt(1 - co * co) / co));

        if (co < 0) {
            ca = Math.PI - ca;
        }

        return r * ca;
    }

    private static BigDecimal degreesToRads(BigDecimal degrees) {
        return (degrees.divide(BigDecimal.valueOf(180), 10, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(Math.PI));
    }

    public static Location locatorToLocation(String locator) {
        Location ubicacion = new Location();
        ubicacion.setGridLocator(locator);

        ubicacion.setLat(BigDecimal.valueOf(-90.00));
        ubicacion.setLon(BigDecimal.valueOf(-180.00));

        locator = padLocator(locator);

        convertPartToLatlon(0, 1, locator, ubicacion);
        convertPartToLatlon(1, 10, locator, ubicacion);
        convertPartToLatlon(2, 10 * 24, locator, ubicacion);
        convertPartToLatlon(3, 10 * 24 * 10, locator, ubicacion);
        convertPartToLatlon(4, 10 * 24 * 10 * 24, locator, ubicacion);

        ubicacion.setGoogleMapsLink(
                String.format("https://maps.google.com/?q=%s,%s", ubicacion.getLat(), ubicacion.getLon()));

        return ubicacion;
    }

    private static void convertPartToLatlon(int counter, int divisor, String locator, Location ubicacion) {
        String gridLon = locator.substring(counter * 2, counter * 2 + 1);
        String gridLat = locator.substring(counter * 2 + 1, counter * 2 + 1 + 1);

        BigDecimal x = BigDecimal.valueOf(10).divide(BigDecimal.valueOf(divisor), 10, RoundingMode.HALF_UP);
        x = BigDecimal.valueOf(l2n(gridLat)).multiply(x);

        BigDecimal y = BigDecimal.valueOf(20).divide(BigDecimal.valueOf(divisor), 10, RoundingMode.HALF_UP);
        y = BigDecimal.valueOf(l2n(gridLon)).multiply(y);

        ubicacion.setLat(ubicacion.getLat().add(x));
        ubicacion.setLon(ubicacion.getLon().add(y));
    }

    private static int l2n(String letter) {
        letter = letter.toLowerCase();
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(letter);
        if (m.matches()) {
            return Integer.parseInt(letter);
        } else {
            return letter.charAt(0) - 97;
        }
    }

    private static String padLocator(String locator) {
        int length = locator.length() / 2;
        StringBuilder sb = new StringBuilder(locator);
        while (length < 5) {
            if ((length % 2) == 1) {
                sb.append("55");
            } else {
                sb.append("LL");
            }

            length = sb.toString().length() / 2;
        }
        return sb.toString();
    }

    public static boolean isValidateGridLocator(String gridLocator) {
        if (gridLocator == null || gridLocator.length() <= 0) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_GRIDLOCATOR_L);
        Matcher m = p.matcher(gridLocator);
        return m.matches();
    }
}
