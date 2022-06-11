package algorithm.nearestNeighbor;

import instances.Instance;
import instances.Properties.VehiclePropertyType;
import model.Location;
import model.Vehicle;
import solution.SolutionContext;
import solution.SolutionContextFactory;
import solution.evaluators.SolutionEvaluator;
import util.PropertiesUtil;
import util.SolutionUtil;
import util.VehicleUtil;

import java.util.*;
import java.util.stream.Collectors;

public class NearestNeighbor {

    public SolutionContext start(Instance instance) {
        SolutionContextFactory solutionContextFactory = new SolutionContextFactory(instance);
        SolutionContext solutionContext = solutionContextFactory.createSolutionContext();

        Vehicle currentVehicle = solutionContext.addVehicle();

        Collection<Location> availableCustomers = new HashSet<>(solutionContext.getCustomers().values());

        while (!availableCustomers.isEmpty()) {

            Location nextLocation = SolutionUtil.getRandomLocation(availableCustomers);

            while (VehicleUtil.canServiceCustomer(currentVehicle, nextLocation)) {
                VehicleUtil.passIntermediateChargingStations(currentVehicle, nextLocation);
                currentVehicle.travelTo(nextLocation);
                availableCustomers.remove(nextLocation);
                nextLocation = SolutionUtil.findNearestLocationFromSet(availableCustomers, currentVehicle.getCurrentLocation());
                if (nextLocation == null) {
                    break;
                }
            }
            VehicleUtil.goToDepot(currentVehicle);
            currentVehicle = solutionContext.addVehicle();
        }
        return solutionContext;
    }
    
}
