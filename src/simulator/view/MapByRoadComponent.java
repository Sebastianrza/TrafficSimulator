package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;


public class MapByRoadComponent extends JComponent implements TrafficSimObserver{
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _ROAD_COLOR_COLOR = Color.BLACK;
	
	private RoadMap _map;

	private Image _car;
	
	private static class Pair{
		int x1, x2, y;
		private Pair(int x1, int x2, int y){
			this.x1 = x1;
			this.x2 = x2;
			this.y = y;
		}
		private int getX1(){
			return x1;
		}
		private int getX2(){
			return x2;
		}
		private int getY(){
			return y;
		}
	}
	
	private List<Pair> postRoads;
	
	public MapByRoadComponent(Controller ctrl){
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() { // me trae la imagen del carro
		_car = loadImage("car.png");
		setPreferredSize (new Dimension (300, 200));
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}	
	}
	
	private void drawRoads(Graphics g) { // en teoria pinta la carretera
		postRoads = new ArrayList<Pair>();
		
		for (Road r : _map.getRoads()) {

			// the road goes from (x1,y) to (x2,y)
			int x1 = 50;
			int y = (/*i*/(_map.getRoads().indexOf(r))+1)*50;
			int x2 = getWidth()-100;

			// draw line from (x1,y) to (x2,y)
			// color BLACK
			drawLine(g, x1, x2, y, _ROAD_COLOR_COLOR);
			//guardo las post de las roads para despues
			postRoads.add(new Pair(x1,x2,y));
			
			// Dibuja los id de las carreteras
			g.drawString(r.getId(), x1 - 40 , y);
			
			//clima de la carretera
			drawweather(g, r, x2, y);
			
			//contaminacion de la carretera
			drawCont(g, r, x2, y);
		}
	}
	protected void drawCont(Graphics g, Road r, int x2, int y){
		int c = (int) Math.floor(Math.min(r.getTotal_contamination()/(1.0 + r.getContLimit()), 1.0) /0.19);
		switch(c){
		case 0:
			g.drawImage(loadImage("cont_0.png"), x2 + 46, y - 16, 32, 32, this);
		break;
		case 1:
			g.drawImage(loadImage("cont_1.png"), x2 + 46, y - 16, 32, 32, this);
		break;
		case 2:
			g.drawImage(loadImage("cont_2.png"), x2 + 46, y - 16, 32, 32, this);
		break;
		case 3:
			g.drawImage(loadImage("cont_3.png"), x2 + 46, y - 16, 32, 32, this);
		break;
		case 4:
			g.drawImage(loadImage("cont_4.png"), x2 + 46, y - 16, 32, 32, this);
		break;
		case 5:
			g.drawImage(loadImage("cont_5.png"), x2 + 46, y - 16, 32, 32, this);
		break;
		}
	}
	
	
	protected void drawweather(Graphics g, Road r, int x2, int y){
		switch(r.getWeather()){
			case SUNNY:
				g.drawImage(loadImage("sun.png"), x2 + 10, y - 16, 32, 32, this);
			break;
			case CLOUDY:
				g.drawImage(loadImage("cloud.png"), x2 + 10, y - 16, 32, 32, this);
			break;
			case RAINY:
				g.drawImage(loadImage("rain.png"), x2 + 10, y - 16, 32, 32, this);
			break;
			case WINDY:
				g.drawImage(loadImage("wind.png"), x2 + 10, y - 16, 32, 32, this);
			break;
			case STORM:
				g.drawImage(loadImage("storm.png"), x2 + 10, y - 16, 32, 32, this);
			break;
			
		}
	}
	
	private void drawVehicles(Graphics g) { // en teoria pinta el carro
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				int x1 = 50;
				int x2 = getWidth()-100;
				int A = v.getLocation();
				int B = v.getRoad().getLength();
				int vX = x1 + (int) ((x2 - x1) * ((double) A / (double) B));
				int vY = this.postRoads.get(_map.getRoads().indexOf(v.getRoad())).getY();
				// draw an image of a car (with circle as background) and it identifier
				g.drawImage(_car, vX, vY - 6, 16, 16, this);
				g.drawString(v.getId(), vX, vY - 6);
			}
		}
	}
	// cambiar los circulos dependiendo del color de su semaforo
	private void drawJunctions(Graphics g) { // en teoria pinta los cruces
		for (Road r : _map.getRoads()) {
			// necesito que el color de los circulos cambie  (este no debe cambiar) 
			g.setColor(_JUNCTION_COLOR);
			// junc scr road
			g.fillOval((postRoads.get(_map.getRoads().indexOf(r)).getX1()) - _JRADIUS / 2, (postRoads.get(_map.getRoads().indexOf(r)).getY()) - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			// necesito que el color de los circulos cambie 
			Color semaforo = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				semaforo = _GREEN_LIGHT_COLOR;
			}
			g.setColor(semaforo);
			// junc dest road
			g.fillOval((postRoads.get(_map.getRoads().indexOf(r)).getX2()) - _JRADIUS / 2, (postRoads.get(_map.getRoads().indexOf(r)).getY()) - _JRADIUS / 2, _JRADIUS, _JRADIUS);

			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().getId(), (postRoads.get(_map.getRoads().indexOf(r)).getX1()), (postRoads.get(_map.getRoads().indexOf(r)).getY())-10);
			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getDest().getId(), (postRoads.get(_map.getRoads().indexOf(r)).getX2()), (postRoads.get(_map.getRoads().indexOf(r)).getY())-10);
		}
	}
	//lleva el control de los objetos a pintar
	private void drawMap(Graphics g) { 
		drawRoads(g);
		drawVehicles(g);
		drawJunctions(g);
	}
	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	
	// actualiza el roadMap y actualiza el pintado 
	public void update(RoadMap map) { 
		_map = map;
		repaint();
	}
	// Busca en la carpeta de icons la imagen del carro correspondiente
	private Image loadImage(String img) { 
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}	
	// This method draws a line from (x1,y) to (x2,y) with an arrow.
	private void drawLine(//
		Graphics g, //
		int x1, //
		int x2, 
		int y, //
		Color lineColor) {
		
		g.setColor(lineColor);
		g.drawLine(x1, y, x2, y);
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

}