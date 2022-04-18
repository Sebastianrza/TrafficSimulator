package simulator.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] _colNames = {"Time","Description"};
	private List<Event> _events;
	
	
	public EventsTableModel(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		_events = null;
		_ctrl.addObserver(this);
	}
	public void update() {
		fireTableDataChanged();
	}

	public void setEventsList(List<Event> event) {
		_events = event;
		Collections.sort(_events, new Sortbyroll());
		update();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch(columnIndex) {
		case 0:
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}
	@Override
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		setEventsList(events);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		List<Event> eventsAux = new ArrayList<>();
		for(Event e : _events) {
			if(e.getTime() > time) {
				eventsAux.add(e);
			}
		}
		setEventsList(eventsAux);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		setEventsList(events);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		setEventsList(events);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		setEventsList(events);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	class Sortbyroll implements Comparator<Event> 
	{ 
	    // Used for sorting in ascending order of 
	    // roll number 
	    @Override
		public int compare(Event a, Event b) 
	    { 
	        return a.getTime() - b.getTime(); 
	    } 
	} 

}
