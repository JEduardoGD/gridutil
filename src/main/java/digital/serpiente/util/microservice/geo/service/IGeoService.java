package digital.serpiente.util.microservice.geo.service;

import digital.serpiente.util.microservice.geo.dto.Location;

public interface IGeoService {

    Location measureDistance(String locatorA, String locatorB);
}
