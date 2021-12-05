package Instances.Properties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Builder
@Setter
@Getter
@NoArgsConstructor
public class VehicleProperty {

	private VehiclePropertyType vehiclePropertyType;

	private Map<String,String> propertyMappings;
}
