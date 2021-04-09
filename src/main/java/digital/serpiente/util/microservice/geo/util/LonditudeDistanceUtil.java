package digital.serpiente.util.microservice.geo.util;

public class LonditudeDistanceUtil {
    private final static int R = 6371; // Radius of the earth
    
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        return LonditudeDistanceUtil.distance( lat1,  lon1,  lat2,  lon2, Double.valueOf(0), Double.valueOf(0));
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, double el1, double el2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}


//double theta = lon1 - lon2;
//double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//dist = Math.acos(dist);
//dist = rad2deg(dist);
//dist = dist * 60 * 1.1515;
//if (unit == 'K') {
//  dist = dist * 1.609344;
//} else if (unit == 'N') {
//  dist = dist * 0.8684;
//  }
//return (dist);
//}
//
///*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
///*::  This function converts decimal degrees to radians             :*/
///*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
//private double deg2rad(double deg) {
//return (deg * Math.PI / 180.0);
//}
//
///*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
///*::  This function converts radians to decimal degrees             :*/
///*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
//private double rad2deg(double rad) {
//return (rad * 180.0 / Math.PI);
//}
//
//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'M') + " Miles\n");
//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'K') + " Kilometers\n");
//System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'N') + " Nautical Miles\n");