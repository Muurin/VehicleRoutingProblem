package utilTests;

import instances.Properties.LocationProperty;
import instances.Properties.LocationPropertyType;
import instances.Properties.PropertyKey;
import instances.Properties.VehiclePropertyType;
import model.Enum.LocationType;
import model.Location;
import model.Vehicle;
import solution.SolutionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testUtil.ObjectInitializers;
import util.CartesianCoordinates;
import util.PropertiesUtil;
import util.VehicleUtil;

public class VehicleUtilTests {

	@Test
	public void initializeDepot(){
		SolutionContext solutionContext=ObjectInitializers.createSolutionContextExample1();
		Vehicle vehicle=solutionContext.addVehicle();
		Assertions.assertEquals("1", PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.DEPARTURE_LOCATION)));
		Assertions.assertTrue(solutionContext.getDepots().containsKey("1"));
		Assertions.assertEquals(1,vehicle.getVehiclePath().size());
		Assertions.assertEquals("1", vehicle.getCurrentLocation().getId());
	}

	@Test
	public void requiredFuel(){
		Assertions.assertEquals(2.0,VehicleUtil.requiredFuel(ObjectInitializers.createSolutionContextExample1().addVehicle()
				, Location
						.builder()
						.cartesianCoordinates(CartesianCoordinates
								.builder()
								.x(1)
								.y(1)
								.build())
						.build(),
				Location
						.builder()
						.cartesianCoordinates(CartesianCoordinates
								.builder()
								.x(3)
								.y(1)
								.build())
						.build()));
	}

	@Test
	public void canServiceCustomer(){
		LocationProperty property=ObjectInitializers.createLocationProperty(LocationPropertyType.DEMAND, PropertyKey.SINGLE_DOUBLE_VALUE,1.0);
		Location location = new Location();
		location.setLocationType(LocationType.CUSTOMER_LOCATION);
		location.getLocationProperties().put(LocationPropertyType.DEMAND,property);
		Assertions.assertTrue(VehicleUtil.canServiceCustomer(ObjectInitializers.createSolutionContextExample1().addVehicle(),location));
	}

	@Test
	public void cantServiceCustomer(){
		LocationProperty property=ObjectInitializers.createLocationProperty(LocationPropertyType.DEMAND, PropertyKey.SINGLE_DOUBLE_VALUE,3.0);
		Location location = new Location();
		location.setLocationType(LocationType.CUSTOMER_LOCATION);
		location.getLocationProperties().put(LocationPropertyType.DEMAND,property);
		Assertions.assertFalse(VehicleUtil.canServiceCustomer(ObjectInitializers.createSolutionContextExample1().addVehicle(),location));
	}

	@Test
	public void canReach(){
		SolutionContext solutionContext= ObjectInitializers.createSolutionContextExample1();
		Assertions.assertTrue(VehicleUtil.canReachLocationAndNearestChargingStation(solutionContext.addVehicle(),solutionContext.getCustomers().get("4")));

	}

	@Test
	public void cantReachCustomerTooFarAway(){
		SolutionContext solutionContext= ObjectInitializers.createSolutionContextExample1();
		Assertions.assertFalse(VehicleUtil.canReachLocationAndNearestChargingStation(solutionContext.addVehicle(),solutionContext.getCustomers().get("2")));
	}

	@Test
	public void cantReachCustomerNoNearbyChargingStation(){
		SolutionContext solutionContext= ObjectInitializers.createSolutionContextExample1();
		Assertions.assertFalse(VehicleUtil.canReachLocationAndNearestChargingStation(solutionContext.addVehicle(),solutionContext.getCustomers().get("9")));
	}

	@Test
	public void chooseIntermediateChargingStation(){
		SolutionContext solutionContext= ObjectInitializers.createSolutionContextExample1();
		Vehicle vehicle=solutionContext.addVehicle();
		Assertions.assertEquals("8",VehicleUtil.chooseIntermediateChargingStation(vehicle,solutionContext.getCustomers().get("3")).getId());
	}

	@Test
	public void canServiceAnyCustomer(){
		SolutionContext solutionContext = ObjectInitializers.createSolutionContextExample1();
		Vehicle vehicle=solutionContext.addVehicle();
		Assertions.assertTrue(VehicleUtil.canServiceAtLeastOneCustomer(vehicle));
	}
}
