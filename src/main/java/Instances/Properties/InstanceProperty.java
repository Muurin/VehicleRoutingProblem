package Instances.Properties;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceProperty extends Property{

	private InstancePropertyType instancePropertyType;
}
