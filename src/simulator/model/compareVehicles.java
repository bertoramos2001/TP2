package simulator.model;

import java.util.Comparator;

public class compareVehicles implements Comparator<Vehicle> {

	@Override
	public int compare(Vehicle v1, Vehicle v2) {
		//devuelve -1 si la location del primero es mayor que la del segundo, 1 si es a la inversa y 0 si son iguales
		//se hace esta comparacion porque se dice que la lista esta ordenada por las localizaciones en orden descendente
		return v1.getLocation() > v2.getLocation() ? -1 : v1.getLocation() < v2.getLocation() ? 1 : 0;
	}

}
