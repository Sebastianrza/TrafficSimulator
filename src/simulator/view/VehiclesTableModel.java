package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;


public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{
	
	private static final long serialVersionUID = 1L;
	private List<Vehicle> vehicles;
	private String _colNames[] = {"Id", "Location", "Itinerary", "CO2 Class", "Max Speed", "Speed", "Total CO2", "CO2 limite", "Distance" };
	
	public VehiclesTableModel(Controller _ctrl) {
		vehicles = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this._colNames.length;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.vehicles.size();
	}
	@Override
	public String getColumnName(int column){
		return this._colNames[column];
		
	}
	private void update(List<Vehicle> v){
		this.vehicles = v;
		fireTableDataChanged();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String s = "";
		Vehicle v = this.vehicles.get(rowIndex);
		switch(columnIndex){
			case 0: //id
				s = v.getId().toString();
				break;
			case 1: //estado del vehiculo
				s = v.getStatus().toString();
				break;
			case 2:	//itinerario
				s = v.getItinerary().toString();
				break;
			case 3:	//clase de c02
				s = "" + v.getContamination();
				break;
			case 4:	//velocidad maxima
				s = "" + v.getMaximum_speed();
				break;
			case 5:	//velocidad actual
				s = "" + v.getCurrent_speed();
				break;
			case 6:	//total de co2  
				s = "" + v.getContamination();
				break;
			case 7:	//limite de co2
				s = "" + v.getTotal_contamination();
				break;
			case 8:	//distancia
				s = "" + v.getTotal_travelled_distance();
				break;
			default:
				assert (false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getVehicles());
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getVehicles());
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}