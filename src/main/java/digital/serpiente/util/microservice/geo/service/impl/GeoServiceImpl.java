package digital.serpiente.util.microservice.geo.service.impl;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

import digital.serpiente.util.microservice.geo.dto.Location;
import digital.serpiente.util.microservice.geo.dto.Response;
import digital.serpiente.util.microservice.geo.service.IGeoService;
import digital.serpiente.util.microservice.geo.util.GridLocatorUtil;
import digital.serpiente.util.microservice.geo.util.Units;

@Service
public class GeoServiceImpl implements IGeoService {
    @Override
    public Response measureDistance(String locatorA, String locatorB) {
        boolean isValidLocatorA = GridLocatorUtil.isValidateGridLocator(locatorA);
        boolean isValidLocatorB = GridLocatorUtil.isValidateGridLocator(locatorB);

        Response response = new Response();

        String error = "";
        if (!isValidLocatorA) {
            error += String.format("El grid locator 1 (%s) no es valido.", locatorA);
        }
        if (!isValidLocatorB) {
            error += String.format("El grid locator 2 (%s) no es valido.", locatorA);
        }
        if (!"".equals(error)) {
            response.setError(error);
            return response;
        }
        Location ubicacionA = GridLocatorUtil.locatorToLocation(locatorA);
        Location ubicacionB = GridLocatorUtil.locatorToLocation(locatorB);
        double distance = GridLocatorUtil.distanceTo(ubicacionA, ubicacionB, Units.KM);

        response.setDistance(new DecimalFormat("#0.00").format(distance));
        response.setLocationA(ubicacionA);
        response.setLocationB(ubicacionB);
        return response;
    }
}
