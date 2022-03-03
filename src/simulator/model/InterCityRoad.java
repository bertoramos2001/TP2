package simulator.model;

public class InterCityRoad extends Road {
	
	private final int SUNNY_CONT_VAR = 2;
	private final int CLOUDY_CONT_VAR = 3;
	private final int RAINY_CONT_VAR = 10;
	private final int WINDY_CONT_VAR = 15;
	private final int STORM_CONT_VAR = 20;
	

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void reduceTotalContamination() {
		int x;
		
		switch(weather) {
			case SUNNY:	
				x = SUNNY_CONT_VAR;
				break;
			case CLOUDY:
				x = CLOUDY_CONT_VAR;
				break;
			case RAINY:	
				x = RAINY_CONT_VAR;
				break;
			case WINDY:	
				x = WINDY_CONT_VAR;
				break;
			default: //aquí solo llegará si no entra en ninguno de los casos anteriores (este es el último caso)
				x = STORM_CONT_VAR;
				break;
		}
		
		totalCont = ((100 - x) * totalCont) / 100;
		
	}

	@Override
	protected void updateSpeedLimit() {
		if (totalCont > contLimit) {
			actualSpeedLimit = maxSpeed / 2;
		} else {
			actualSpeedLimit = maxSpeed;
		}
		
	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		
		if (weather == Weather.STORM) {
			return (actualSpeedLimit * 8) / 10;
		} else {
			return actualSpeedLimit;
		}
		
	}

}
