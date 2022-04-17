package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	private List<Junction> junctions;
	private String cols[] = {"Id", "Green", "Queues"}; 
	
	public JunctionsTableModel(Controller ctrl){
		junctions = new ArrayList<Junction>();
		ctrl.addObserver(this);
		
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return cols.length;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.junctions.size();
	}
	@Override
	public String getColumnName(int column){
		return this.cols[column];
	}
	private void update(List<Junction> junctions){
		this.junctions = junctions;
		fireTableDataChanged();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		String s = "";
		Junction j = this.junctions.get(rowIndex);
		switch(columnIndex){
			case 0: 
				s = "" + j.getId();
				break;
			case 1:
				s = j.getGreenLightIndex() == -1 ? "NONE" : j.getInRoads().get(j.getGreenLightIndex()).getId();
				break;
			case 2:
				for(Road r: j.getInRoads()){
					s = s + "" + r.getId() + ":" + j.getQueue(r).toString();
				}
				break;
			default:
				assert (false);
		}
		return s;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.update(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this.update(map.getJunctions());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.update(map.getJunctions());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.update(map.getJunctions());
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
