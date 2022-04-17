package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar  extends JPanel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	int time = 0;
	protected JLabel t;
	protected JLabel t_act;
	protected JLabel ev;
	public StatusBar(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		initGUI();
		_ctrl.addObserver(this);
		
	}
	
	protected void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		t = new JLabel("Time: ", SwingConstants.LEFT);
		t_act = new JLabel("");
		this.add(t);
		this.add(t_act);
		this.add(new JSeparator(SwingConstants.VERTICAL));
		
		ev = new JLabel("");
		this.add(ev);
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		t_act.setText(""+ time);
		ev.setText("");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		t_act.setText(""+ time);
		ev.setText("Event added ("+e.toString()+")");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		t_act.setText(""+ time);
		ev.setText("");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		t_act.setText(""+ time);
		ev.setText("Welcome!");
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
