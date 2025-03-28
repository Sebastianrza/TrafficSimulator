package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{

	

	public NewCityRoadEvent(int time, String id, String src, String dest, int length,  int co2limit, int maxSpeed, Weather weather) {
		super(time, id, src, dest, length, co2limit, maxSpeed, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Road createRoad(String id, Junction src, Junction dest, int maxSpeed, int co2limit, int length , Weather weather) {
		CityRoad r = null;
		try {
			r = new CityRoad(id, src, dest, maxSpeed, co2limit, length, weather);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	public String toString() {
		return "New Inter City Road: '"+id+"', "+src+"', "+dest+"', "+maxSpeed+"', "+weather+"', "+co2limit+"'";
	}
	

}
