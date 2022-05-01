package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VehicleState {

    private double currentTime = 0;
    private double currentLoad;
    private double currentFuel;
    private double currentDistance = 0;


}
