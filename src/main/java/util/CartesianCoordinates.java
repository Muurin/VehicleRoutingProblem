package util;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartesianCoordinates {

	private double x;
	private double y;

	@Override
	public String toString() {
		return "CartesianCoordinates{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
