package solution.evaluators;

import instances.Properties.LocationPropertyType;
import model.Enum.LocationType;
import model.Location;
import model.Vehicle;
import model.paths.Route;
import solution.SolutionContext;
import util.PropertiesUtil;

public class TimeWindowEvaluator implements SolutionEvaluator{

    @Override
    public double evaluate(SolutionContext solution) {

        double score=0;
        for(Vehicle vehicle:solution.getVehicles().values()){
            score+=vehicle.getCurrentTime();
            double currentVehicleTime = 0;
            for(Route route: vehicle.getVehiclePath()){
                currentVehicleTime+= route.getTimeSpent();
                Location currentLocation = route.getDestinationLocation();
                if(currentLocation.getLocationType().equals(LocationType.CUSTOMER_LOCATION)){

                    //is due date for start of unloading or the whole service?
                    double unloadingTime = PropertiesUtil.getDoublePropertyValue(currentLocation.getLocationProperties().get(LocationPropertyType.SERVICE_TIME));
                    double dueDate = PropertiesUtil.getDoublePropertyValue(currentLocation.getLocationProperties().get(LocationPropertyType.DUE_DATE));
                    double readyTime = PropertiesUtil.getDoublePropertyValue(currentLocation.getLocationProperties().get(LocationPropertyType.READY_TIME));

                    double currentTimeWithoutUnloading = currentVehicleTime - unloadingTime;

                    if(currentTimeWithoutUnloading < readyTime){
                        score += readyTime - currentVehicleTime;
                    }

                    if(currentTimeWithoutUnloading > dueDate){
                        score += currentTimeWithoutUnloading - dueDate;
                    }

                }
            }
        }
        solution.reset();
        return score;
    }
}
