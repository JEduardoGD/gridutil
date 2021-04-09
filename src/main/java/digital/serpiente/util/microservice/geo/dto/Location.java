package digital.serpiente.util.microservice.geo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Location {
    private BigDecimal lat;
    private BigDecimal lon;
}
