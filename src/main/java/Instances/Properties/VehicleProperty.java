package Instances.Properties;

import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleProperty extends  Property{

	private VehiclePropertyType vehiclePropertyType;

}
