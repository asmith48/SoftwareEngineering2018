package model.vehicles;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Collection;

import model.infrastructure.Direction;
import model.infrastructure.gate.CrossingGate;


/**
 * Very basic car factory.  Creates the car and registers it with the crossing gate and the car infront of it.
 * @author jane
 *
 */
public class CarFactory {
	
	private Collection<CrossingGate> gates = null;
	private Car previousCar = null;
	private ArrayList<Car> cars = new ArrayList<Car>();
	Point location;
	Direction direction;
	
	public CarFactory(){}
	
	public CarFactory(Direction direction, Point location, Collection<CrossingGate> gates){
		this.direction = direction;
		this.location = location;
		this.gates = gates;
	}
	
	
	// Most code here is to create random speeds
	public Car buildCar(){
		if (previousCar == null || 
				(direction==Direction.SOUTH && location.y < previousCar.getVehicleY()-100) ||
				(direction==Direction.EAST && location.x > previousCar.getVehicleX()+50)){
			Car car = new Car(location.x,location.y,direction);	
			double speedVariable = (Math.random() * 10)/10;
			car.setSpeed((2-speedVariable)*1.5); 
			
			// All cars created by this factory must be aware of crossing gates in the road
			for(CrossingGate gate: gates){
				gate.addObserver(car);
				if(gate != null && gate.getTrafficCommand()=="STOP")
					car.setGateDownFlag(true);
			}
			
			// Each car must observe the car infront of it so it doesn't collide with it.
			if (previousCar != null)
				previousCar.addObserver(car);
			previousCar = car;
			
			cars.add(car);
			return car;
		} else 
			return null;
	}
	
	public Car insertCar(){
	// This method is used to insert cars from EastWEst road car factory into the Skyway road car factory. 
		Car car = new Car(location.x,800,direction); // y position will be even with EastWest road
		double speedVariable = (Math.random() * 10)/10;
		car.setSpeed((2-speedVariable)*1.5); 
		
		// if possible, find cars on Skyway road directly ahead of and behind new car
		Car carAhead = null;
		double carAheadY = 0.0;
		Car carBehind = null;
		double carBehindY = 0.0;
		for (Car cCar: cars) {
			if (cCar.getVehicleY() >= 825 && cCar.getVehicleY() < carAheadY) {
				carAheadY = cCar.getVehicleY();
				carAhead = cCar;
			} else if (cCar.getVehicleY() <= 775 && cCar.getVehicleY() > carBehindY) {
				carBehindY = cCar.getVehicleY();
				carBehind = cCar;
			}
		}
		
		// make car behind observer of new car and new car observer of car ahead
		if (carBehind != null && carAhead != null) {
			carAhead.deleteObserver(carBehind);
			carAhead.addObserver(car);
			car.addObserver(carBehind);
		} else if (carBehind != null) {
			car.addObserver(carBehind);
		} else if (carAhead != null) {
			carAhead.addObserver(car);
		}
		
		cars.add(car);
		return car;
	}
	
	public ArrayList<Car> getCars() {
		return cars;
	}
	
	public void removeCar(Car c) {
		if (cars==null || c==null) return;
		
		int currIndex = cars.indexOf(c);
		Car carBehind = null;
		Car carInFront = null;
		
		// get car directly behind and car directly ahead of car to be removed
		if (currIndex+1 < cars.size())
			carBehind = cars.get(currIndex+1);
		if (currIndex-1 >= 0)
			carInFront = cars.get(currIndex-1);
		
		// delete all relationships involving the car to be removed and make car behind an observer of car ahead
		if (carBehind != null && carInFront != null && !carInFront.offScreen()) {
			carInFront.deleteObserver(c);
			c.deleteObserver(carBehind);
			carInFront.addObserver(carBehind);
		} else if (carInFront != null && !carInFront.offScreen()) {
			carInFront.deleteObserver(c);
		} else if (carBehind != null) { 
			if (direction==Direction.SOUTH) carBehind.resetLeadY(-1);
			else carBehind.resetLeadX(-1);
			c.deleteObserver(carBehind);
		}
	
		cars.remove(c);
	}

	// We will get a concurrency error if we try to delete cars whilst iterating through the array list
	// so we perform this in two stages.
	// 1.  Loop through the list and identify which cars are off the screen.  Add them to 'toDelete' array.
	// 2.  Iterate through toDelete and remove the cars from the original arrayList.
	public ArrayList<Car> removeOffScreenCars() {
		// Removing cars from the array list.
		ArrayList<Car> toDelete = new ArrayList<Car>();
		for(Car car: cars){
			if (direction==Direction.SOUTH) car.move();	
			else if (direction==Direction.EAST) car.moveHorizontal();
			if (car.offScreen())
				toDelete.add(car);
			
		}   
		for (Car car: toDelete)
			cars.remove(car);
		return toDelete;
	}
}
