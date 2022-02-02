package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if(sim!=null) {
			this.sim = sim;
		}else {
			throw new IllegalArgumentException("This simulator is null");
		}
		
		if(eventsFactory != null) {
			this.eventsFactory = eventsFactory;
		}else {
			throw new IllegalArgumentException("The Events Factory is null");
		}
	}
	
	//Getter&Setters
		public TrafficSimulator getSim() {
			return sim;
		}

		public void setSim(TrafficSimulator sim) {
			this.sim = sim;
		}

		public Factory<Event> getEventsFactory() {
			return eventsFactory;
		}

		public void setEventsFactory(Factory<Event> eventsFactory) {
			this.eventsFactory = eventsFactory;
		}
		
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		
		if(!jo.has("events") || jo.getJSONArray("events") == null ) {
			throw new IllegalArgumentException("The event is null or empty");
		}else { 
			JSONArray ja =  jo.getJSONArray("events");
			for(int i = 0; i < ja.length(); i++) {
				if(ja.getJSONObject(i)== null) {
					throw new IllegalArgumentException("This Object is null or empty");
				}else {
					Event e = eventsFactory.createInstance(ja.getJSONObject(i));
					sim.addEvent(e);
				}
			}
		}
	}
	
	public void run (int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println(" \"states\": [");
		for (int i = 0; i<n; i++) {
			this.sim.advance();
			p.print(this.sim.report());
			if(i!=n-1)p.println(",");
		}
		p.println("");
		p.println("]");
		p.println("}");
	}
	
	public void reset() {
		this.sim.reset();
	}
}
