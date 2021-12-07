package Instances.Properties;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleProperty {

	private VehiclePropertyType vehiclePropertyType;

	private Map<String, String> propertyMappings = new HashMap<>();
}
