package homework4;

import java.awt.Point;
import java.util.Observable;

public class Ship extends Observable {
	Point currentLocation;
	OceanMap oceanMap;
	
	Ship(int x, int y, OceanMap oceanMap) {
		currentLocation = new Point(x, y);
		this.oceanMap = oceanMap;
	}
	
	public Point getShipLocation() {
		//return current location of ship
		return currentLocation;
	}
	
	public void goEast() {
		//move right on right arrow key press if move is within grid dimensions and right square is empty
		if (currentLocation.x + 1 < oceanMap.dimensions 
				&& oceanMap.oceanGrid[currentLocation.x + 1][currentLocation.y] == 0) {
			currentLocation.x  = currentLocation.x + 1;
			setChanged();
			notifyObservers(); //notify pirate observers of move
		}
	}
	
	public void goWest() {
		//move left on left arrow key press if move is within grid dimensions and left square is empty
		if (currentLocation.x - 1 >= 0 
				&& oceanMap.oceanGrid[currentLocation.x - 1][currentLocation.y] == 0) {
			currentLocation.x = currentLocation.x - 1;
			setChanged();
			notifyObservers();  //notify pirate observers of move
		}
	}
	
	public void goNorth() {
		//move up on up arrow key press if move is within grid dimensions and above square is empty
		if (currentLocation.y - 1 >= 0
				&& oceanMap.oceanGrid[currentLocation.x][currentLocation.y - 1] == 0) {
			currentLocation.y = currentLocation.y - 1;
			setChanged();
			notifyObservers(); //notify pirate observers of move
		}
	}
	
	public void goSouth() {
		//move down on down arrow key press if move is within grid dimensions and below square is empty
		if (currentLocation.y + 1 < oceanMap.dimensions
				&& oceanMap.oceanGrid[currentLocation.x][currentLocation.y + 1] == 0) {
			currentLocation.y = currentLocation.y + 1;
			setChanged();
			notifyObservers();  //notify pirate observers of move
		}
	}
	
}
