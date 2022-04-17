package simulator.model;

public class InterCityRoad extends Road{
	
	

	protected InterCityRoad(String id, Junction src, Junction dest, int maximum_speed, int contLimit, int length,
			Weather weather) {
		super(id, src, dest, maximum_speed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		if(weather == Weather.SUNNY) 
		{
			this.total_contamination = (int) (((100.0-2)*total_contamination)/100.0);
			
		}else if(weather == Weather.CLOUDY) {
			this.total_contamination = (int) (((100.0-3)*total_contamination)/100.0);
		}
		else if(weather == Weather.RAINY) {
			this.total_contamination = (int) (((100.0-10)*total_contamination)/100.0);
		}
		else if(weather == Weather.WINDY) {
			this.total_contamination = (int) (((100.0-15)*total_contamination)/100.0);
		}
		else if(weather == Weather.STORM) {
			this.total_contamination = (int)(((100.0-20)*total_contamination)/100.0);
		}
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		if(total_contamination >= contLimit){
			current_speed_limit = (int)(maximum_speed*0.5);
		}else {
			current_speed_limit = maximum_speed;
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		if(this.weather.equals(Weather.STORM)) {
			int Current_speed = (int) (this.current_speed_limit*0.8);
			return Current_speed;
		}else {
			int Current_speed = v.getCurrent_speed();
			Current_speed = this.current_speed_limit;
			return Current_speed;
		}
	}

		
}
