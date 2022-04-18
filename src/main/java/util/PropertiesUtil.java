package util;

import instances.Properties.Property;
import instances.Properties.PropertyKey;

public class PropertiesUtil {

	public static double getDoublePropertyValue(Property property) {

		try {
			return ((Double) property.getPropertyMappings().get(PropertyKey.SINGLE_DOUBLE_VALUE));

		} catch (Exception e) {
			throw new RuntimeException("Krivi tip propertia");
		}
	}

	public static String getStringPropertyValue(Property property) {

		try {
			return ((String) property.getPropertyMappings().get(PropertyKey.SINGLE_STRING_VALUE));

		} catch (Exception e) {
			throw new RuntimeException("Krivi tip propertia");
		}
	}

	public static void setDoublePropertyValue(Property property, Double value){
		try {
			property.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE,value);

		} catch (Exception e) {
			throw new RuntimeException("Krivi tip propertia");
		}
	}


}
