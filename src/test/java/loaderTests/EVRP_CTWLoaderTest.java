package loaderTests;

import InstanceLoaders.EVRP_CTWInstanceLoader;
import InstanceLoaders.InstanceLoader;
import Instances.Instance;

import Instances.Properties.VehiclePropertyType;
import Model.Vehicle;
import algorithm.solution.SolutionContext;
import algorithm.solution.VRPInstancePaths;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.PropertiesUtil;

import java.io.IOException;

public class EVRP_CTWLoaderTest {

	@Test
	public void testLoader() throws IOException, JDOMException {

		String instancePath= VRPInstancePaths.getEVRP_CTWPath1();

		InstanceLoader instanceLoader = new EVRP_CTWInstanceLoader();
		Instance instance= instanceLoader.load(instancePath);

		Assertions.assertEquals(122,instance.getLocations().size());

		SolutionContext solutionContext=new SolutionContext(instance);
		Assertions.assertEquals(21,solutionContext.getChargingStations().size());
		Assertions.assertEquals(100,solutionContext.getCustomers().size());

		Vehicle vehicle=solutionContext.addVehicle();

		Assertions.assertEquals(79.69, PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY)));
		Assertions.assertEquals(200.0,PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.VEHICLE_LOAD_CAPACITY)));
		Assertions.assertEquals(1.0,PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.FUEL_CONSUMPTION_RATE)));
		Assertions.assertEquals(3.39,PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.INVERSE_REFUELING_RATE)));
		Assertions.assertEquals(1.0,PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.AVERAGE_VELOCITY)));
		Assertions.assertEquals("D0",PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));
		Assertions.assertEquals("D0",PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.DEPARTURE_LOCATION)));
	}
}
