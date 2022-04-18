package loaderTests;

import instanceLoaders.EVRP_CTWInstanceLoader;
import instanceLoaders.InstanceLoader;
import instances.Instance;

import instances.Properties.VehiclePropertyType;
import model.Vehicle;
import algorithm.solution.SolutionContext;
import algorithm.solution.SolutionContextFactory;
import algorithm.solution.VRPInstancePaths;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.PropertiesUtil;

import java.io.IOException;

public class EVRP_CTWLoaderTest {

	@Test
	public void testLoader() throws IOException, JDOMException {

//		String instancePath= VRPInstancePaths.getEVRP_CTWPath1();
		String instancePath= VRPInstancePaths.getEVRP_CTWPath2();

		InstanceLoader instanceLoader = new EVRP_CTWInstanceLoader();
		Instance instance= instanceLoader.load(instancePath);

		Assertions.assertEquals(122,instance.getLocations().size());

		SolutionContextFactory solutionContextFactory = new SolutionContextFactory(instance);
		SolutionContext solutionContext = solutionContextFactory.createSolutionContext();

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
