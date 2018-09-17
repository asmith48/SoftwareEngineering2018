package homework4;

import java.awt.Point;
import java.util.Observer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Observable;

public class Pirate implements Observer {
	Point CCSLocation; //ship location
	Point currentLocation; //pirate location
	Image pirateImage;
	ImageView pirateImageView;
	OceanMap oceanMap;
	int scale;
	
	Pirate(int x, int y, int scale, OceanMap oceanMap) {
		currentLocation = new Point(x, y);
		this.oceanMap = oceanMap;
		oceanMap.oceanGrid[x][y] = 2; //set oceanGrid to filled
		this.scale = scale;
		// create pirate image in constructor
		pirateImage = new Image(getClass().getResource("pirateship.gif").toString(),25,25,true,true);
		pirateImageView = new ImageView(pirateImage);
	}
	
	public Point getShipLocation() {
		//get current pirate location
		return currentLocation;
	}
	
	public ImageView getImageView() {
		return pirateImageView;
	}
	
	public void moveShip() {
		//move closer to observable ship, update image position 
		if (currentLocation.x - CCSLocation.x < 0)
			currentLocation.x++;
		else
			currentLocation.x--;
		
		if (currentLocation.y - CCSLocation.y < 0)
			currentLocation.y++;
		else
			currentLocation.y--;
	
		pirateImageView.setX(currentLocation.x*scale);
		pirateImageView.setY(currentLocation.y*scale);
		oceanMap.oceanGrid[currentLocation.x][currentLocation.y] = 2; //update oceanGrid
	}
	
	@Override
	public void update(Observable s, Object arg1) {
		//update pirate location if ship location is updated
		if (s instanceof Ship){
			CCSLocation = ((Ship)s).getShipLocation();
			moveShip();			
		}		
		
	}
}
