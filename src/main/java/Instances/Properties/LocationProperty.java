package Instances.Properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class LocationProperty extends Property {

	private LocationPropertyType locationPropertyType;

}
