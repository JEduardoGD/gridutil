package digital.serpiente.util.microservice.geo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import digital.serpiente.util.microservice.geo.dto.Location;

public class GridLocatorUtil {


    public double distanceTo(Location startLatLon, Location endLatLon, String unit){
        int r = 6371;

        switch(unit) {
           case "m":
               r *= 1000;
        }

        BigDecimal hn = degreesToRads(startLatLon.getLat());
        BigDecimal he = degreesToRads(startLatLon.getLon());
        BigDecimal n  = degreesToRads(endLatLon.getLat());
        BigDecimal e  = degreesToRads(endLatLon.getLon());

        double co = Math.cos(he.subtract(e).doubleValue()) * Math.cos(hn.doubleValue()) * Math.cos(n.doubleValue()) + Math.sin(hn.doubleValue()) * Math.sin(n.doubleValue());
        double ca = Math.atan(Math.abs(Math.sqrt(1-co*co) / co));

        if (co <0) {
            ca = Math.PI - ca;
        }

        return r * ca;
    }

    private BigDecimal degreesToRads(BigDecimal degrees) {
        return (degrees.divide(BigDecimal.valueOf(180))).multiply(BigDecimal.valueOf(Math.PI));
    }
    
    public static Location locatorToLocation(String locator) {
        Location ubicacion = new Location();

        ubicacion.setLat(BigDecimal.valueOf(-90.00));
        ubicacion.setLon(BigDecimal.valueOf(-180.00));

        locator = padLocator(locator);
        
        ubicacion = convertPartToLatlon(0, 1, locator, ubicacion);
        ubicacion = convertPartToLatlon(1, 10, locator, ubicacion);
        ubicacion = convertPartToLatlon(2, 10 * 24, locator, ubicacion);
        ubicacion = convertPartToLatlon(3, 10 * 24 * 10, locator, ubicacion);
        ubicacion = convertPartToLatlon(4, 10 * 24 * 10 * 24, locator, ubicacion);
        
        return ubicacion;
    }

    private static Location convertPartToLatlon(int counter, int divisor, String locator, Location ubicacion) {
        String gridLon = locator.substring(counter*2, counter*2 + 1);
        String gridLat = locator.substring(counter*2+1, counter*2+1+1);
        
        BigDecimal x = BigDecimal.valueOf(10).divide(BigDecimal.valueOf(divisor), RoundingMode.HALF_UP);
        x = BigDecimal.valueOf(l2n(gridLat)).multiply(x);
        
        BigDecimal y = BigDecimal.valueOf(20).divide(BigDecimal.valueOf(divisor), RoundingMode.HALF_UP);
        y = BigDecimal.valueOf(l2n(gridLon)).multiply(y);
        
        ubicacion.setLat(ubicacion.getLat().add(x));
        ubicacion.setLon(ubicacion.getLon().add(y));
        return ubicacion;
    }
    


    private static int l2n(String letter) {
        letter = letter.toLowerCase();
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(letter);
        if (m.matches()) {
            return Integer.valueOf(letter).intValue();
        } else {
            return letter.charAt(0) - 97;
        }
    }

    private static String padLocator(String locator) {
        int length = locator.length() / 2;

        while (length < 5) {
            if ((length % 2) == 1) {
                locator += "55";
            } else {
                locator += "LL";
            }

            length = locator.length() / 2;
        }
        return locator;
    }
}
