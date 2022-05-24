package temp;

import algorithm.geneticAlgorithm.executables.preanalisys.Constants;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instances.Instance;
import model.Location;
import model.Vehicle;
import model.paths.Route;
import solution.SolutionContext;
import solution.SolutionContextFactory;
import util.MathUtil;
import util.SolutionUtil;
import util.VehicleUtil;

import java.io.IOException;

public class DebugProba {

    public static void main(String[] args) throws IOException {


        Instance instance = new EVRP_CTWInstanceLoader().load(Constants.instancesPath + "rc105_21.txt");
        SolutionContext solutionContext = new SolutionContextFactory(instance).createSolutionContext();
        Vehicle vehicle = solutionContext.addVehicle();
        vehicle.setCurrentFuel(22.6812228745);
        vehicle.getVehiclePath().add(new Route(solutionContext.getCustomers().get("C75"), solutionContext.getCustomers().get("C75")));


        Location nearestCH = SolutionUtil.findNearestLocationFromSet(solutionContext.getChargingStations().values(), solutionContext.getCustomers().get("C75"));


        System.out.println(MathUtil.getEuclideanDistanceTo(nearestCH, solutionContext.getCustomers().get("C75")));
        while (!VehicleUtil.canReachLocationAndNearestChargingStation(vehicle, solutionContext.getCustomers().get("C54"))) {

            try {
                Location chargingStation = VehicleUtil.chooseIntermediateChargingStation(vehicle, solutionContext.getCustomers().get("C54"));
                vehicle.travelTo(chargingStation);

            }catch (NullPointerException e){
                System.out.println("Current fuel - "+vehicle.getCurrentFuel() +"\n"
                        +"Current location - " + vehicle.getCurrentLocation()  +"\n"
                        +"Target location - " + vehicle);
                throw new RuntimeException();
            }
            //            System.out.println("MEDUPUNJENJE! - "+ currentVehicle.getCurrentLocation().getId() + " " + chargingStation.getId()+ " "+ destination.getId());
        }
    }

}

