package digital.serpiente.util.microservice.geo.dto;

import lombok.Data;

@Data
public class Response {
    private Location locationA;
    private Location locationB;
    private String distance;
}
