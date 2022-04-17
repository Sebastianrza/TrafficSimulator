package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton fileChoose;
	private JButton changeClssCont;
	private JButton changeWeather;
	private JButton run;
	private JButton stop;
	private JSpinner ticks;
	private JButton exit;
	private JFileChooser file;
	private String fFilePath;
	private Controller ctrl;
	private boolean _stopped;
	private int time = 0;
	private int currentTime = 0;
	public ControlPanel(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		
		this.setLayout(new BorderLayout());
		this.ctrl = _ctrl;
		this.ctrl.addObserver(this);
		
		JToolBar toolBar = new JToolBar("controlPanel");
		file = new JFileChooser(new File("resources/examples/"));
		fFilePath = new String();
		
		this.fileChoose = new JButton();
		this.fileChoose.setToolTipText("Open a File");
		this.fileChoose.setIcon(new ImageIcon("resources/icons/open.png"));
		toolBar.add(this.fileChoose);
		
		toolBar.addSeparator();
		
		this.changeClssCont = new JButton();
		this.changeClssCont.setToolTipText("Change Contamination");
		this.changeClssCont.setIcon(new ImageIcon("resources/icons/co2class.png"));
		toolBar.add(this.changeClssCont);
		
		this.changeWeather = new JButton();
		this.changeWeather.setToolTipText("Change Weather");
		this.changeWeather.setIcon(new ImageIcon("resources/icons/weather.png"));
		toolBar.add(this.changeWeather);
		
		this.run = new JButton();
		this.run.setToolTipText("Run Simulation");
		this.run.setIcon(new ImageIcon("resources/icons/run.png"));
		toolBar.add(this.run);
		
		this.stop = new  JButton();
		this.stop.setToolTipText("Stop Simulation");
		this.stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		toolBar.add(this.stop);
		
		toolBar.add(new JLabel("Ticks:"));
		
		this.ticks = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
		this.ticks.setToolTipText("Simulation tick to run: 1 - 10000");
		this.ticks.setMaximumSize(new Dimension(80,40));
		this.ticks.setMinimumSize(new Dimension(80,40));
		this.ticks.setPreferredSize(new Dimension(80,40));
		toolBar.add(this.ticks);
		
		this.exit = new JButton();
		this.exit.setToolTipText("Exit Simulation");
		this.exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		
		this.add(toolBar, BorderLayout.LINE_START);
		this.add(this.exit, BorderLayout.LINE_END);
		
		this.fileChoose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = file.showOpenDialog(null);
				
				if(r == JFileChooser.APPROVE_OPTION) {
					fFilePath = (file.getSelectedFile().getAbsolutePath());
	
				}
				
				if(r == JFileChooser.CANCEL_OPTION) {
					fFilePath = null;
				}
				
				if(fFilePath != null) {
					try {
						InputStream fileInStream = new FileInputStream(new File(fFilePath));
						ctrl.reset();
						ctrl.loadEvents(fileInStream);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Archivo incorrecto no puede leerse", "Error al cargar el archivo", JOptionPane.WARNING_MESSAGE);
						ex.printStackTrace();
					}
				}

			}
		});
		
		
		this.changeClssCont.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChangeCO2ClassDialog co = new ChangeCO2ClassDialog(ctrl, (JFrame) SwingUtilities.getWindowAncestor(changeClssCont));
			}
			
		});
		this.changeWeather.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
           
            	ChangeWeatherDialog cw = new ChangeWeatherDialog(_ctrl,  (JFrame) SwingUtilities.getWindowAncestor(changeWeather));
            }
        });
		
		this.run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_stopped = false;
				time = (int)ticks.getValue() - currentTime;
				
				run_sim(time);
			}
			
		});
		
		this.stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stop();
			}
			
		});
		
		this.exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(null, "Si pulsa aceptar se cerrara el programa", "cerrar la simulación", JOptionPane.OK_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
			
		});
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.currentTime = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.currentTime = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	private void run_sim(int n) {
		JButtonStatus(false);
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
			// TODO show error message;
			_stopped = true;
			return;
		}
			SwingUtilities.invokeLater(new Runnable() {
			@Override
				public void run() {
					
					run_sim(n - 1);
				}
			});
		} else {
			currentTime = 0;
			time = 0;
			JButtonStatus(true);
			_stopped = true;
		}
	}
	
	private void stop() {
		JButtonStatus(true);
		_stopped = true;
	}
	
	protected void JButtonStatus(boolean b){
		fileChoose.setEnabled(b);
    	changeClssCont.setEnabled(b);
    	changeWeather.setEnabled(b);
    	run.setEnabled(b);
    	ticks.setEnabled(b);
    	exit.setEnabled(b);
	}
}
