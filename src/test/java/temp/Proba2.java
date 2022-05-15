package temp;

import solution.SolutionContext;
import solution.SolutionContextFactory;
import solution.VRPInstancePaths;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instanceLoaders.InstanceLoader;
import instances.Instance;
import model.Location;
import model.Vehicle;
import org.jdom2.JDOMException;
import util.VehicleUtil;

import java.io.IOException;

public class Proba2 {


    public static void main(String[] args) throws IOException, JDOMException {
        String instancePath = VRPInstancePaths.getEVRP_CTWPath2();

        InstanceLoader instanceLoader = new EVRP_CTWInstanceLoader();
        Instance instance = instanceLoader.load(instancePath);

        SolutionContextFactory solutionContextFactory = new SolutionContextFactory(instance);

        SolutionContext solutionContext = solutionContextFactory.createSolutionContext();

        Vehicle vehicle =  solutionContext.addVehicle();

        Location location = solutionContext.getCustomers().get("C19");
        Location location1= solutionContext.getCustomers().get("C81");

        Location location2= solutionContext.getChargingStations().get("S7");

        double fuelFromCustomerToCustomer = VehicleUtil.requiredFuel(vehicle, location, location1);
        System.out.println(fuelFromCustomerToCustomer);
        double fuelFromStationToCustomer = VehicleUtil.requiredFuel(vehicle, location2, location1);
        System.out.println(fuelFromStationToCustomer);

        vehicle.travelTo(location2);
        System.out.println(vehicle.getCurrentFuel());
        System.out.println(VehicleUtil.canReachLocation(vehicle,location1));
        System.out.println(VehicleUtil.canReachLocationAndNearestChargingStation(vehicle,location1));

        Location loc = VehicleUtil.chooseIntermediateChargingStation(vehicle,location1);
        System.out.println(loc);
        System.out.println(VehicleUtil.canReachLocationAndNearestChargingStation(vehicle,loc));

    }
}
