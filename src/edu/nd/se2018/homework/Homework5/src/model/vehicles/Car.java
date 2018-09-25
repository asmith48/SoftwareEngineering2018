package model.vehicles;

import java.util.Observable;


import java.util.Observer;

import model.infrastructure.Direction;
import model.infrastructure.gate.CrossingGate;
import view.CarImageSelector;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Represents Car object
 * @author jane
 *
 */
public class Car extends Observable implements IVehicle, Observer{
	private ImageView ivCar;
	private double currentX = 0;
	private double currentY = 0;
	private double originalY = 0;
	private double originalX = 0;
	private boolean gateDown = false;
	private double leadCarY = -1;  // Current Y position of car directly infront of this one
	private double leadCarX = -1;
	private double speed = 0.5;
	private Direction direction;
		
	/**
	 * Constructor
	 * @param x initial x coordinate of car
	 * @param y initial y coordinate of car
	 */
	public Car(int x, int y, Direction d){
		this.currentX = x;
		this.currentY = y;
		originalY = y;
		originalX = x;
		ivCar = new ImageView(CarImageSelector.getImage());
		ivCar.setX(getVehicleX());
		ivCar.setY(getVehicleY());
		this.direction = d;
	}
		
	@Override
	public Node getImageView() {
		return ivCar;
	}
	
	public Image getImage() {
		return ivCar.getImage();
	}
	
	public void setImage(Image i) {
		this.ivCar = new ImageView(i);
		this.ivCar.setX(getVehicleX());
		this.ivCar.setY(getVehicleY());
	}
	
	public boolean gateIsClosed(){
		return gateDown;
	}
	
	public double getVehicleX(){
		return currentX;
	}
	public double getVehicleY(){
		return currentY;
	}
	
	public void resetLeadY(double d) {
		leadCarY = d;
	}
	
	public void resetLeadX(double d) {
		leadCarX = d;
	}
	
	public void move(){
		boolean canMove = true; 
		
		// First case.  Car is at the front of the stopping line.
		if (gateDown && getVehicleY() < 430 && getVehicleY()> 390) {
			canMove = false;}
		
		// Second case. Car is too close too other car.
		if (leadCarY != -1  && getDistanceToLeadCar() < 50) {
			
			canMove = false;}
		
		if (canMove){
			currentY+=speed;
			ivCar.setY(currentY);
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void moveHorizontal() {
		// move cars on EastWest road in horizontal direction
		boolean canMove = true;
		
		if (getVehicleX() > 415 && getVehicleX() < 455)
			canMove = false;
		
		if (leadCarX != -1 && getDistanceToLeadCar() < 50)
			canMove = false;
		
		if (canMove) {
			currentX-=speed;
			ivCar.setX(currentX);
		}
		
		setChanged();
		notifyObservers();
	}
	
	public boolean switchRoads() {
		// check that car is at intersection of Western Highway and EastWest roads
		if  (currentY<801 && currentY>799) {
			// allow random number of cars to enter new road (~25%)
			if ((int)(Math.random() * 100) >25 && (int)(Math.random() * 100) <51){
				return true;
			}
		}
		return false;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public void setGateDownFlag(boolean gateDown){
		this.gateDown = gateDown;
	}
	
	public boolean offScreen(){
		if (direction==Direction.SOUTH && currentY > 1020)
			return true;
		else if (direction==Direction.EAST && currentX < 415)
			return true;
		else
			return false;			
	}
		
	public void reset(){
		if (direction==Direction.SOUTH) currentY = originalY;
		else currentX = originalX;
	}
	
	public double getDistanceToLeadCar(){
		if (direction==Direction.SOUTH) return Math.abs(leadCarY-getVehicleY());
		else return Math.abs(getVehicleX()-leadCarX);
	}
	
	public void removeLeadCar(){
		if (direction==Direction.SOUTH) leadCarY = -1;
		else leadCarX = -1;
	}

	@Override
	public void update(Observable o, Object arg1) {
		if (o instanceof Car){
			if (direction==Direction.SOUTH) {
				leadCarY = (((Car)o).getVehicleY());
				if (leadCarY > 1020)
					leadCarY = -1;
			} else if (direction==Direction.EAST) {
				leadCarX = (((Car)o).getVehicleX());
				if (leadCarX < 415)
					leadCarX = -1;
			}
		}
			
		if (o instanceof CrossingGate){
			CrossingGate gate = (CrossingGate)o;
			if(gate.getTrafficCommand()=="STOP")			
				gateDown = true;
			else
				gateDown = false;
					
		}				
	}	
}
