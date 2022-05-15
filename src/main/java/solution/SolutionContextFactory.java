package solution;

import instances.Instance;
import model.Location;
import model.VehicleFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SolutionContextFactory {

    private final Instance instance;

    public SolutionContext createSolutionContext(){

        SolutionContext solutionContext = new SolutionContext();

        for (Location location : instance.getLocations()) {
            switch (location.getLocationType()) {
                case CUSTOMER_LOCATION -> solutionContext.getCustomers().put(location.getId(), location);
                case DEPOT -> solutionContext.getDepots().put(location.getId(), location);
                case RECHARGING_STATION -> solutionContext.getChargingStations().put(location.getId(), location);
            }
        }

        solutionContext.setInstanceProperties(instance.getInstanceProperties());
        solutionContext.setVehicleFactory(new VehicleFactory(solutionContext, instance.getVehicleProperties()));

        return solutionContext;
    }

}
