
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import model.infrastructure.MapBuilder;
import model.infrastructure.RailwayTracks;
import model.infrastructure.Road;
import model.infrastructure.gate.CrossingGate;
import model.vehicles.Car;
import model.vehicles.Train;
import view.MapDisplay;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Simulation extends Application{
	
	private Pane root;
	private Scene scene;
	private MapBuilder mapBuilder;
	private MapDisplay mapDisplay;
	
	@Override  
	public void start(Stage stage) throws Exception {
		
		root = new Pane();
		
		// Build infrastructure
		mapBuilder = new MapBuilder();
		mapDisplay = new MapDisplay(root, mapBuilder.getRoads().values(), mapBuilder.getTracks(),mapBuilder.getAllGates());					
		mapDisplay.drawTracks();		
		mapDisplay.drawRoad();
		mapDisplay.drawGate();
		
		scene = new Scene(root,1200,1000);
		stage.setTitle("Railways");
		stage.setScene(scene);
		stage.show();
				
		// Create two trains, one on track Royal going west, one on track Second going east
		RailwayTracks track = mapBuilder.getTrack("Royal");	
		RailwayTracks track2 = mapBuilder.getTrack("Second");	
		HashMap<String, Train> trains = new HashMap<String,Train>();
		trains.put("train1", new Train(track.getEndX()+100,track.getEndY()-25,track.getDirection()));
		trains.put("train2", new Train(track2.getStartX()-100,track2.getStartY()-25,track2.getDirection()));
		for (Train train: trains.values()) root.getChildren().add(train.getImageView());
		
		// Add gates as observers of trains
		for(CrossingGate gate: mapBuilder.getAllGates()) {
			for (Train train: trains.values()) train.addObserver(gate);
		}
				
		// Sets up a repetitive loop i.e., in handle that runs the actual simulation
		new AnimationTimer(){

			@Override
			public void handle(long now) {
			
				createCar();
				
				for (Train train: trains.values()) train.move();
				
				for(CrossingGate gate: mapBuilder.getAllGates())
					gate.operateGate();
				
				for (Train train: trains.values()) {
					if (train.offScreen()) train.reset();
				}
					
				checkRoadSwitch(); //check if car can switch from Western Highway to EastWest road and make change
				enterSkyway(); //check if car can switch from EastWest to Skyway road and make change
				clearCars();				
			}
		}.start();
	}
	
	private void checkRoadSwitch() {
	//move car from Western Highway to EastWest
		Car newEastWestCar = null;
		Road westernHighway = mapBuilder.getRoads().get("Western Highway");
		Road eastWest = mapBuilder.getRoads().get("EastWest");
		if (westernHighway.getCarFactory() != null) { //check that there are cars on Western Highway
			ArrayList<Car> cars = westernHighway.getCarFactory().getCars();
			for (Car car: cars) {
				if (car.switchRoads()) { // check that car is at valid position to switch
					newEastWestCar = car;
					break;
				}
			}
		}
		if (newEastWestCar != null && newEastWestCar.getImage() != null) {
			Car car = eastWest.getCarFactory().buildCar(); //add car to EastWest road
			Image imageCopy = newEastWestCar.getImage(); //copy image from old car to created car
			if (imageCopy!=null)
				car.setImage(imageCopy);
			if (car != null){
				root.getChildren().add(car.getImageView()); //add new car to screen
			}
			westernHighway.getCarFactory().removeCar(newEastWestCar); //remove old car from Western Highway car factory
			root.getChildren().remove(newEastWestCar.getImageView()); //remove old car from screen
		}
		
	}
	
	private void enterSkyway() {
		ArrayList<Car> cars = mapBuilder.getRoads().get("EastWest").getCarFactory().getCars();
		if (cars.size() > 0) { //check that there are cars on EastWest road
			Car firstCar = cars.get(0);  
			if (firstCar.getVehicleX() > 415 && firstCar.getVehicleX() < 455) { //check that first car in line is at end of road
			
				if (skywayClear()) { //check that no cars are currently blocking Skyway road
					Car skywayCar = mapBuilder.getRoads().get("Skyway").getCarFactory().insertCar(); //insert EastWest car into Skyway car factory
					Image imageCopy = firstCar.getImage(); // copy EastWest car image to new Skyway car
					if (imageCopy!=null)
						skywayCar.setImage(imageCopy);
					if (skywayCar != null)
						root.getChildren().add(skywayCar.getImageView());
					
					mapBuilder.getRoads().get("EastWest").getCarFactory().removeCar(firstCar); //remove old car from EastWest road car factory
					root.getChildren().remove(firstCar.getImageView()); //remove old image from EastWest road
				}
			}
		}	
	}
	
	private boolean skywayClear() {
	// check that there are no cars currently blocking Skyway road at EastWest intersection
		boolean clear = true;
		ArrayList<Car> skywayCars = mapBuilder.getRoads().get("Skyway").getCarFactory().getCars();
		for (Car c: skywayCars) {
			if (c.getVehicleY() < 850 && c.getVehicleY() > 750) 
				clear = false;
		}
		return clear;
	}
	
	// Clears cars as they leave the simulation
	private void clearCars(){
		Collection<Road> roads = mapBuilder.getRoads().values();
		for(Road road: roads){			
			if (road.getCarFactory()!= null){
				ArrayList<Car> junkCars = road.getCarFactory().removeOffScreenCars();	
				mapDisplay.removeCarImages(junkCars);
			}
		}
	}
	
	private void createCar(){
		Collection<Road> roads = mapBuilder.getRoads().values();
		for(Road road: roads){
			if (road.getCarFactory() != null && road.getRoadName() != "EastWest"){
					if ((int)(Math.random() * 100) == 15){
						Car car = road.getCarFactory().buildCar();
						if (car != null){
							root.getChildren().add(car.getImageView());
						}
					}
			}
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}
}

