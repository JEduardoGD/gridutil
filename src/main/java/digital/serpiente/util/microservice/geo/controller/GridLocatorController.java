package digital.serpiente.util.microservice.geo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import digital.serpiente.util.microservice.geo.dto.Response;
import digital.serpiente.util.microservice.geo.service.IGeoService;

@RestController
public class GridLocatorController {
    
    @Autowired private IGeoService geoService;
    
    @GetMapping("/measuredistance/{gridLocatorOne}/and/{gridLocatorTwo}")
    public Response measureDistance(@PathVariable("gridLocatorOne") String gridLocatorOne,
            @PathVariable("gridLocatorTwo") String gridLocatorTwo) {
        return geoService.measureDistance(gridLocatorOne, gridLocatorTwo);
    }
}