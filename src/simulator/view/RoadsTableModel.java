package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	private List<Road> roads;
	private String _colNames[] = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	public RoadsTableModel(Controller _ctrl) {
		roads = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getColumnCount() {
		return this._colNames.length;
	}
	@Override
	public int getRowCount() {
		return this.roads.size();
	}
	@Override
	public String getColumnName(int column){
		return this._colNames[column];
		
	}
	private void update(List<Road> r){
		this.roads = r;
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		String s = "";
		Road r = this.roads.get(rowIndex);
		switch(columnIndex){
			case 0: //id
				s = "" + r.getId();
				break;
			case 1: //longitud de la carretera
				s = "" + r.getLength();
				break;
			case 2:	//condiciones climaticas
				s = "" + r.getWeather();
				break;
			case 3:	//velocidad maxima
				s = "" + r.getMaximum_speed();
				break;
			case 4:	//velocidad actual
				s = "" + r.getCurrent_speed_limit();
				break;
			case 5:	//CO2 total
				s = "" + r.getTotal_contamination();
				break;
			case 6:	//limite de CO2
				s = "" + r.getContLimit();
				break;
			default:
				assert (false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map.getRoads());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map.getRoads());
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}