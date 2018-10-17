package homework06;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chip implements Observer {
	Point currentLocation;
	Random rand = new Random();
	int scale;
	BoardMap boardMap;
	ImageView chipImageView;
	ObservableList<Node> root;
	Image chipImage;
	boolean chipCollected = false;
	
	Chip(int scale, BoardMap boardMap, ObservableList<Node> root) {
		currentLocation = new Point(rand.nextInt(boardMap.dimensions)+0, rand.nextInt(boardMap.dimensions)+0);
		while (boardMap.boardGrid[currentLocation.x][currentLocation.y]!=0) {
			currentLocation = new Point(rand.nextInt(boardMap.dimensions)+0, rand.nextInt(boardMap.dimensions)+0);
		}
		boardMap.boardGrid[currentLocation.x][currentLocation.y] = 3;
		this.boardMap = boardMap;
		this.scale = scale;
		this.root = root;
		
		chipImage = new Image("textures//chipItem.png",scale,scale,true,true);
		chipImageView = new ImageView(chipImage);
		chipImageView.setX(currentLocation.x*scale);
		chipImageView.setY(currentLocation.y*scale);
		root.add(chipImageView);
	}
	
	public Point getLocation() {
		return currentLocation;
	}
	
	public ImageView getImageView() {
		return chipImageView;
	}
	
	public boolean chipCollected() {
	// return number of chips collected
		return chipCollected;
	}
	
	@Override
	public void update(Observable p, Object arg) {
		if (p instanceof Player){
			// check if player and chip object on same tile
			Point playerLocation = ((Player)p).getPlayerLocation();
			if (playerLocation.x == currentLocation.x && playerLocation.y == currentLocation.y) {
				//if player on chip, remove chip from screen and mark chip as collected
				root.remove(chipImageView);
				chipCollected = true;
			}
		}
	}

}
