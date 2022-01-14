package Instances.Properties;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Property {

	@Builder.Default
	private Map<PropertyKey, Object> propertyMappings = new HashMap<>();
}
