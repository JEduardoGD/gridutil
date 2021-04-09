package digital.serpiente.util.microservice.geo.service.impl;

import org.springframework.stereotype.Service;

import digital.serpiente.util.microservice.geo.dto.Location;
import digital.serpiente.util.microservice.geo.service.IGeoService;
import digital.serpiente.util.microservice.geo.util.GridLocatorUtil;

@Service
public class GeoServiceImpl implements IGeoService {
    @Override
    public Location measureDistance(String locatorA, String locatorB) {
        Location ubicacionA = GridLocatorUtil.locatorToLocation(locatorA);
//        Ubicacion ubicacionB = GridLocatorUtil.locatorToUbicacion(locatorB);
        return ubicacionA;
    }
}
