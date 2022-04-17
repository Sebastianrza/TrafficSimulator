package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Road> roadBox;
	private JComboBox<Weather> weatherBox;
	private JSpinner ticksLabel;

	@SuppressWarnings("unchecked")
	public ChangeWeatherDialog(Controller ctrl, JFrame p){
		
		super(p);
		this.setSize(600,150);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		JPanel label = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		roadBox = new JComboBox(ctrl.getSim().getRoadmap().getRoads().toArray());
		roadBox.setToolTipText("simulation Roads");
		roadBox.setMaximumSize(new Dimension(80, 20));
    	roadBox.setMinimumSize(new Dimension(80, 20));
    	roadBox.setPreferredSize(new Dimension(80, 20));
		
    	weatherBox = new JComboBox<Weather>(Weather.values()); 
		weatherBox.setToolTipText("simulation Weather");
		weatherBox.setMaximumSize(new Dimension(80, 20));
		weatherBox.setMinimumSize(new Dimension(80, 20));
		weatherBox.setPreferredSize(new Dimension(80, 20));
		
		ticksLabel = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
    	ticksLabel.setToolTipText("Simulation tick to run: 1-10000");
		ticksLabel.setMaximumSize(new Dimension(60, 20));
		ticksLabel.setMinimumSize(new Dimension(60, 20));
		ticksLabel.setPreferredSize(new Dimension(60, 20));
		
		label.add(new Label("Road: "));
    	label.add(roadBox);
    	label.add(new Label("Weather: "));
    	label.add(weatherBox);
    	label.add(new Label("Ticks: "));
    	label.add(ticksLabel);
    	
    	this.add(new JLabel("<html> Schedule an event to change the weather of road after a given number of simulation ticks from now.<html>"), BorderLayout.PAGE_START);
    	this.add(label, BorderLayout.CENTER);
		
    	 JButton cancela = new JButton("Cancel");
         cancela.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) {
         		setVisible(false);
             }
 		});
         
         JPanel panelB = new JPanel();
         getContentPane().add(panelB, BorderLayout.SOUTH);
         panelB.setBackground(Color.white);
         
         
         JButton oka = new JButton("OK");
         oka.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) {
         		List<Pair<String, Weather>> cs = new ArrayList<>();
         		cs.add(new Pair<String,Weather> (roadBox.getSelectedItem().toString(), (Weather) weatherBox.getSelectedItem()));
         		try {
         				int n = (ctrl.getSim().getSimulation_time()+(int)ticksLabel.getValue());
						ctrl.addEvent(new SetWeatherEvent(n,cs));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
             	setVisible(false);
             }
 		});
         
         panelB.add(oka);
         panelB.add(cancela);
         
         
         this.pack();
     	this.setLocationRelativeTo(null);
	}

}
