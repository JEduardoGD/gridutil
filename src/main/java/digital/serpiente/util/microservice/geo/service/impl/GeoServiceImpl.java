package digital.serpiente.util.microservice.geo.service.impl;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import digital.serpiente.util.microservice.geo.dto.Location;
import digital.serpiente.util.microservice.geo.dto.Response;
import digital.serpiente.util.microservice.geo.service.IGeoService;
import digital.serpiente.util.microservice.geo.util.AwsClientBuilder;
import digital.serpiente.util.microservice.geo.util.GridLocatorUtil;
import digital.serpiente.util.microservice.geo.util.Units;
import software.amazon.awssdk.services.location.LocationClient;
import software.amazon.awssdk.services.location.model.Place;
import software.amazon.awssdk.services.location.model.SearchForPositionResult;
import software.amazon.awssdk.services.location.model.SearchPlaceIndexForPositionRequest;
import software.amazon.awssdk.services.location.model.SearchPlaceIndexForPositionRequest.Builder;
import software.amazon.awssdk.services.location.model.SearchPlaceIndexForPositionResponse;

@Service
public class GeoServiceImpl implements IGeoService {
    private static final String NOT_VALID_GRID_LOCATOR = "grid locator no valido";
    
    @Value("${aws.access.key.id}")
    private String awsAccessKeyId;
    
    @Value("${aws.access.key.secret}")
    private String awsAccessKeySecret;
    
    @Value("${aws.location.indexname}")
    private String awsLocationndexname;


    @Override
    public Response measureDistance(String locatorA, String locatorB) {
        boolean isValidLocatorA = GridLocatorUtil.isValidateGridLocator(locatorA);
        boolean isValidLocatorB = GridLocatorUtil.isValidateGridLocator(locatorB);

        Response response = new Response();
        Location ubicacionA = new Location();
        Location ubicacionB = new Location();
        boolean isError = false;

        if (!isValidLocatorA) {
            ubicacionA.setError(NOT_VALID_GRID_LOCATOR);
            isError = true;
        }
        if (!isValidLocatorB) {
            ubicacionB.setError(NOT_VALID_GRID_LOCATOR);
            isError = true;
        }
        if (isError) {
            response.setLocationA(ubicacionA);
            response.setLocationB(ubicacionB);
            return response;
        }
        ubicacionA = GridLocatorUtil.locatorToLocation(locatorA);
        ubicacionA.setRegion(getRegionOfLocation(ubicacionA));

        ubicacionB = GridLocatorUtil.locatorToLocation(locatorB);
        ubicacionB.setRegion(getRegionOfLocation(ubicacionB));

        double distance = GridLocatorUtil.distanceTo(ubicacionA, ubicacionB, Units.KM);

        response.setDistance(new DecimalFormat("#0.00").format(distance));
        response.setLocationA(ubicacionA);
        response.setLocationB(ubicacionB);
        return response;
    }

    @Override
    public Location getStateOfLocation(String gridLocator) {
        boolean location = GridLocatorUtil.isValidateGridLocator(gridLocator);

        if (!location) {
            Location ubicacion = new Location();
            ubicacion.setError(NOT_VALID_GRID_LOCATOR);
            return ubicacion;
        }

        Location ubicacion = GridLocatorUtil.locatorToLocation(gridLocator);
        ubicacion.setRegion(getRegionOfLocation(ubicacion));
        return ubicacion;
    }

    @Override
    public String getRegionOfLocation(Location location) {
        LocationClient awsLocationClient = null;
        try {
            awsLocationClient = AwsClientBuilder.createAmzRouteS3Client(awsAccessKeySecret, awsAccessKeyId);
            Builder builder = SearchPlaceIndexForPositionRequest.builder();
            List<Double> position = Arrays.asList(location.getLon().doubleValue(), location.getLat().doubleValue());
            builder.position(position);
            builder.indexName(awsLocationndexname);
            builder.maxResults(20);
            SearchPlaceIndexForPositionRequest x = builder.build();
            SearchPlaceIndexForPositionResponse response = awsLocationClient.searchPlaceIndexForPosition(x);
            List<Place> places = response.results().stream().map(SearchForPositionResult::place)
                    .collect(Collectors.toList());
            if (places != null && !places.isEmpty()) {
                Place place = places.get(0);
                return String.format("%s/%s", place.country(), place.region());
            }
        } finally {
            if (awsLocationClient != null) {
                awsLocationClient.close();
            }
        }
        return null;
    }
}
