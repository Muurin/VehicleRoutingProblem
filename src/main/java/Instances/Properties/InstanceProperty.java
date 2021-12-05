package Instances.Properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class InstanceProperty {

	private InstancePropertyType instancePropertyType;

	private Map<String,String> propertyMappings;

}
