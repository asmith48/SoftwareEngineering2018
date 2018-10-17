package homework06;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Observable implements Observer{
	Point currentLocation;
	BoardMap boardMap;
	int scale;
	Image playerImage;
	ImageView playerImageView;
	boolean gameOver = false;
	
	Player(int scale, BoardMap boardMap) {
		Random rand = new Random();
		currentLocation = new Point(rand.nextInt(boardMap.dimensions)+0, rand.nextInt(boardMap.dimensions)+0);
		while (boardMap.boardGrid[currentLocation.x][currentLocation.y]!=0) {
			currentLocation = new Point(rand.nextInt(boardMap.dimensions)+0, rand.nextInt(boardMap.dimensions)+0);
		}
		this.boardMap = boardMap;
		this.scale = scale;
		
		playerImage = new Image("textures//chipDown.png",25,25,true,true);
		setImageView();
	}
	
	public Point getPlayerLocation() {
	// return current location of player
		return currentLocation;
	}
	
	public void setImageView() {
	// update image view with new image
		playerImageView = new ImageView(playerImage);
		playerImageView.setX(currentLocation.x*scale);
		playerImageView.setY(currentLocation.y*scale);
	}
	
	public ImageView getImageView() {
		return playerImageView;
	}
	
	public void goEast() {
		//move right on right arrow key press if move is within grid dimensions and right square is empty
		if (currentLocation.x + 1 < boardMap.dimensions 
				&& boardMap.boardGrid[currentLocation.x + 1][currentLocation.y] != 1) {
			currentLocation.x  = currentLocation.x + 1;
			
			// update player image
			playerImage = new Image("textures//chipRight.png",25,25,true,true);
			setImageView();
			
			setChanged();
			notifyObservers();
		}
	}
	
	public void goWest() {
		if (currentLocation.x - 1 >= 0 
				&& boardMap.boardGrid[currentLocation.x - 1][currentLocation.y] != 1) {
			currentLocation.x  = currentLocation.x - 1;
			
			//update player image
			playerImage = new Image("textures//chipLeft.png",25,25,true,true);
			setImageView();
			
			setChanged();
			notifyObservers();
		}
	}
	
	public void goNorth() {
		if (currentLocation.y - 1 >= 0
				&& boardMap.boardGrid[currentLocation.x][currentLocation.y - 1] != 1) {
			currentLocation.y  = currentLocation.y - 1;
			
			// update player image
			playerImage = new Image("textures//chipUp.png",25,25,true,true);
			setImageView();
			
			setChanged();
			notifyObservers();
		}
	}
	
	public void goSouth() {
		if (currentLocation.y + 1 < boardMap.dimensions
				&& boardMap.boardGrid[currentLocation.x][currentLocation.y + 1] != 1) {
			currentLocation.y  = currentLocation.y + 1;
			
			// update player image
			playerImage = new Image("textures//chipDown.png",25,25,true,true);
			setImageView();
			
			setChanged();
			notifyObservers();
		}
	}

	public boolean gameOver() {
	// if gameOver set to true, signify game over
		return gameOver;
	}
	
	@Override
	public void update(Observable b, Object arg) {
		if (b instanceof Bug){
			// check if player and bug object on same tile, is so set gameOver var to true
			Point bugLocation = ((Bug)b).getLocation();
			if (bugLocation.x == currentLocation.x && bugLocation.y == currentLocation.y) {
				gameOver = true; 
			}
		}
	}

}
