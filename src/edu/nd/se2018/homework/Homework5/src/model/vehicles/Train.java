package model.vehicles;

import java.util.Observable;
import model.infrastructure.Direction;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the train entity object
 * @author jane
 *
 */
public class Train extends Observable implements IVehicle{
	private double currentX = 0;
	private double currentY = 0;
	private double originalX = 0;
	private Image img;
	private ImageView imgView;
	private int trainLength = 35;
	Direction direction;
	
	public Train(int x, int y, Direction direction){
		this.currentX = x;
		this.currentY = y;
		originalX = x;
		img = new Image("Train.png",120,trainLength,false,false);
		imgView = new ImageView(img);
		imgView.setX(currentX);
		imgView.setY(currentY);
		this.direction = direction;
	}
	
	public double getVehicleX(){
		return currentX;
	}
	
	public double getVehicleY(){
		return currentY;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void move(){
		// move left if track direction is west, else move right
		if (this.direction==Direction.WEST)
			currentX-=2;
		else
			currentX+=2;
		imgView.setX(currentX);
		setChanged();
		notifyObservers();
	}
	
	public boolean offScreen(){
		// check if train exits screen to left for west direction or right for east direction
		if (this.direction==Direction.WEST && currentX < -200)
			return true;
		else if (this.direction==Direction.EAST && currentX > 1400)
			return true;
		else
			return false;				
	}
	
	public void reset(){
		currentX = originalX;
	}

	//@Override
	public Node getImageView() {
		return imgView;
	}
}
