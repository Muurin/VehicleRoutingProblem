package Instances.Properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class InstanceProperty {

	private InstancePropertyType instancePropertyType;

	private Map<String,String> propertyMappings;

}
