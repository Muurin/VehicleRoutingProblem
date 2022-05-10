package model;

import instances.Properties.LocationProperty;
import instances.Properties.LocationPropertyType;
import model.Enum.LocationType;
import lombok.*;
import util.CartesianCoordinates;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	private String id;

	private CartesianCoordinates cartesianCoordinates;

	private LocationType locationType;

	@Builder.Default
	private Map<LocationPropertyType, LocationProperty> locationProperties = new HashMap<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Location)) return false;
		Location location = (Location) o;
		return getId().equals(location.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString(){
		return id;
	}
}
