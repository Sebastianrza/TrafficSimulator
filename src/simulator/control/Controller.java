package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.Observable;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller implements Observable<TrafficSimObserver>{
	
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
					
					sim.addEvent(eventsFactory.createInstance(ja.getJSONObject(i)));
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
	
	public void run(int n){
		for (int i = 0; i<n; i++) {
			this.sim.advance();
			this.sim.report();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void reset() {
		this.sim.reset();
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		sim.addObserver(o);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		sim.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		sim.addEvent(e);
	} 
}
