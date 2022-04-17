package simulator.view;

import java.awt.BorderLayout;
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
import simulator.model.NewSetContClassEvent;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Vehicle> vehicleBox;
    private JSpinner co2Class;
    private JSpinner ticksLabel;

	@SuppressWarnings("unchecked")
	public ChangeCO2ClassDialog(Controller ctrl, JFrame p) {
		// TODO Auto-generated constructor stub
		
		super(p);
		this.setSize(600, 150);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
    	JPanel label = new JPanel(new FlowLayout(FlowLayout.LEADING));
    	
    	vehicleBox = new JComboBox(ctrl.getSim().getRoadmap().getVehicles().toArray()); 
    	vehicleBox.setToolTipText("simulation Vehicle");
    	vehicleBox.setMaximumSize(new Dimension(80, 20));
    	vehicleBox.setMinimumSize(new Dimension(80, 20));
    	vehicleBox.setPreferredSize(new Dimension(80, 20));
    	
    	co2Class = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
    	co2Class.setToolTipText("Simulation Co2 class: 0-10");
		co2Class.setMaximumSize(new Dimension(80, 20));
		co2Class.setMinimumSize(new Dimension(80, 20));
		co2Class.setPreferredSize(new Dimension(80, 20));
		
		ticksLabel = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
    	ticksLabel.setToolTipText("Simulation tick to run: 1-10000");
		ticksLabel.setMaximumSize(new Dimension(60, 20));
		ticksLabel.setMinimumSize(new Dimension(60, 20));
		ticksLabel.setPreferredSize(new Dimension(60, 20));
		
		label.add(new Label("Vehicle: "));
    	label.add(vehicleBox);
    	label.add(new Label("CO2 Class: "));
    	label.add(co2Class);
    	label.add(new Label("Ticks: "));
    	label.add(ticksLabel);
    	
    	this.add(new JLabel("<html> Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now<html>"), BorderLayout.PAGE_START);
    	this.add(label, BorderLayout.CENTER);
    	
    	 JButton cancela = new JButton("Cancel");
         cancela.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
         		setVisible(false);
             }
 		});
         
         JPanel panelB = new JPanel();
         getContentPane().add(panelB, BorderLayout.SOUTH);
      
         
         JButton oka = new JButton("OK");
         oka.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
           	List<Pair<String, Integer>> cs = new ArrayList<>();
         		cs.add(new Pair<String,Integer>(vehicleBox.getSelectedItem().toString(), (int)co2Class.getValue()));
         		try {
         				int n = (ctrl.getSim().getSimulation_time()+(int)ticksLabel.getValue());
						ctrl.addEvent(new NewSetContClassEvent (n,cs));
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
