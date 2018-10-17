package homework06;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bug extends Observable{
	Random rand = new Random();
	Point location;
	BoardMap boardMap;
	int scale;
	ObservableList<Node> root;
	Image bugImage;
	ImageView bugImageView;
	boolean vertical;
	boolean movingUp = true;
	boolean movingLeft = true;

	Bug(int scale, BoardMap boardMap, ObservableList<Node> root, boolean vertical) {
		location = new Point(rand.nextInt(boardMap.dimensions)+0, rand.nextInt(boardMap.dimensions)+0);
		while (boardMap.boardGrid[location.x][location.y]!=0) {
			location = new Point(rand.nextInt(boardMap.dimensions)+0, rand.nextInt(boardMap.dimensions)+0);
		}
		boardMap.boardGrid[location.x][location.y] = 4;
		
		this.boardMap = boardMap;
		this.scale = scale;
		this.root = root;
		
		bugImage = new Image("textures//bugUp.png",scale,scale,true,true);
		setImageView();
		
		this.vertical = vertical;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setImageView() {
	// update image view with new image
		bugImageView = new ImageView(bugImage);
		bugImageView.setX(location.x*scale);
		bugImageView.setY(location.y*scale);
	}
	
	public ImageView getImageView() {
		return bugImageView;
	}
	
	public void move() {
		if (vertical) {
		// move bug up and down
			if (movingUp && location.y - 1 >= 0 && boardMap.boardGrid[location.x][location.y - 1] != 1) {
				moveUp();
			} else if (location.y + 1 < boardMap.dimensions && boardMap.boardGrid[location.x][location.y + 1] != 1) {
				movingUp = false;
				moveDown();
			} else {
				movingUp = true;
			}
		} else {
		// move bug left and right
			if (movingLeft && location.x - 1 >= 0 && boardMap.boardGrid[location.x - 1][location.y] != 1) {
				moveLeft();
			} else if (location.x + 1 < boardMap.dimensions && boardMap.boardGrid[location.x + 1][location.y] != 1) {
				movingLeft = false;
				moveRight();
			} else {
				movingLeft = true;
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void moveUp() {
	// move up and reset image
		location.y  = location.y - 1;
		bugImage = new Image("textures//bugUp.png",25,25,true,true);
		setImageView();
	}
	
	public void moveDown() {
	// move down and reset image
		location.y  = location.y + 1;
		bugImage = new Image("textures//bugDown.png",25,25,true,true);
		setImageView();
	}
	
	public void moveLeft() {
	// move left and reset image
		location.x  = location.x - 1;
		bugImage = new Image("textures//bugLeft.png",25,25,true,true);
		setImageView();
	}
	
	public void moveRight() {
	// move right and reset image
		location.x  = location.x + 1;
		bugImage = new Image("textures//bugRight.png",25,25,true,true);
		setImageView();
	}

}
