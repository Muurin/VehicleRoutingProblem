package utilTests;


import Instances.Properties.Property;
import Instances.Properties.PropertyKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
//TODO write tests for exception handling
public class PropertiesUtilTest {

	@Test
	public void getDoublePropertyValue(){
		Map<PropertyKey,Object> propertyMappings= new HashMap<>();
		propertyMappings.put(PropertyKey.SINGLE_DOUBLE_VALUE,5.0);
		Property p=Property.builder().propertyMappings(propertyMappings).build();
		Assertions.assertEquals(5.0, PropertiesUtil.getDoublePropertyValue(p));
	}

	@Test
	public void getStringPropertyValue(){
		Map<PropertyKey,Object> propertyMappings= new HashMap<>();
		propertyMappings.put(PropertyKey.SINGLE_STRING_VALUE,"A");
		Property p=Property.builder().propertyMappings(propertyMappings).build();
		Assertions.assertEquals("A", PropertiesUtil.getStringPropertyValue(p));
	}

}
