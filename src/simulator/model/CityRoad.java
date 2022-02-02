package simulator.model;

public class CityRoad extends Road {

	

	protected CityRoad(String id, Junction src, Junction dest, int maximum_speed, int contLimit, int length,
			Weather weather) {
		super(id, src, dest, maximum_speed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		if(this.weather.equals(Weather.WINDY) || this.weather.equals(Weather.STORM)) {
			if(this.total_contamination-10 < 0) {
				this.total_contamination = 0;
			}else {
				this.total_contamination = this.total_contamination - 10;
			}
			
		}else {
			if(this.total_contamination - 2 < 0){
				this.total_contamination = 0;
			}else{
				this.total_contamination = this.total_contamination - 2;
			}
		}
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		this.current_speed_limit = this.maximum_speed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		int s = this.current_speed_limit;
		int f = v.getContamination();
		int Current_speed = v.getCurrent_speed();
		Current_speed = (int)(((11.0-f)*s)/11.0);
		return Current_speed;
	}

}
