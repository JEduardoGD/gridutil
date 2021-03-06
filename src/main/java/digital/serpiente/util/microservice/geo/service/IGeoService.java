package digital.serpiente.util.microservice.geo.service;

import digital.serpiente.util.microservice.geo.dto.Location;
import digital.serpiente.util.microservice.geo.dto.Response;

public interface IGeoService {

    Response measureDistance(String locatorA, String locatorB);

    Location getStateOfLocation(String gridLocator);

    String getRegionOfLocation(Location location);
}
