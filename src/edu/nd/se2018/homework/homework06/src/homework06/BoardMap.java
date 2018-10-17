package homework06;

import javafx.collections.ObservableList;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;


public class BoardMap {
	int[][] boardGrid = new int[25][25]; //25x25 board grid
	final int dimensions = 25; //dimensions of board
	Image squareImage;
	ImageView sqImageView;
	Random rand = new Random();
	Point portal;
	ImageView portalImageView;
	ArrayList<ImageView> tileImageViews;

	
	public void drawMap(ObservableList<Node> root, int scale) {
		//draw 25x25 grid, all squares contain 0 value to show that they're empty
		for (int x=0; x<dimensions; x++) {
			for (int y=0; y<dimensions; y++) {
				squareImage = new Image("textures//BlankTile.png",scale,scale,true,true);
				sqImageView = new ImageView(squareImage);
				sqImageView.setX(x*scale);
				sqImageView.setY(y*scale);
				root.add(sqImageView); 
				
				boardGrid[x][y] = 0; //empty square
			}
		}
	}
	
	public void createPortal(ObservableList<Node> root, int scale) {
	// create one portal object on empty square of grid
		portal = new Point(rand.nextInt(dimensions)+0, rand.nextInt(dimensions)+0);
		while (boardGrid[portal.x][portal.y]!=0) {
			portal = new Point(rand.nextInt(dimensions)+0, rand.nextInt(dimensions)+0);
		}
		
		boardGrid[portal.x][portal.y] = 2;
		Image portalImage = new Image("textures//portal.png",scale,scale,true,true);
		portalImageView = new ImageView(portalImage);
		portalImageView.setX(portal.x*scale);
		portalImageView.setY(portal.y*scale);
		root.add(portalImageView);
	}
	
	public ImageView getPortalIV() {
		return portalImageView;
	}
	
	public Point getPortalLocation() {
		return portal;
	}
	
	public ArrayList<ImageView> getWallIV() {
		return tileImageViews;
	}
	
	public void createMaze(ObservableList<Node> root, int scale, int level) {
	// create maze on game board 
		ArrayList<Point> wall = new ArrayList<Point>(); //list of points to add to maze wall
		// add points on border of grid
		for (int i=0; i<dimensions; i++) {
			wall.add(new Point(i,0));
			wall.add(new Point(i,dimensions-1));
			wall.add(new Point(0,i));
			wall.add(new Point(dimensions-1,i));
		}
		
		int count = 0;
		// fill wall with 1/4 of board tiles
		while (count <= (dimensions*dimensions)/4) {
			int length = rand.nextInt(10);
			Point currSq = new Point(rand.nextInt(dimensions)+0, rand.nextInt(dimensions)+0);
			ArrayList<Point> line = new ArrayList<Point>();
			line.add(currSq);
			Point nextSq;
			int direction = rand.nextInt(4);
			int i=0;
			// create lines
			while (i<=length) {
				if (direction==0) { //SOUTH
					nextSq = new Point(currSq.x, currSq.y+1);
				} else if (direction==1) { //NORTH
					nextSq = new Point(currSq.x, currSq.y-1);
				} else if (direction==2) { //EAST
					nextSq = new Point(currSq.x-1, currSq.y);
				} else { //WEST
					nextSq = new Point(currSq.x+1, currSq.y);
				}
				
				if (nextSq.x>=0 && nextSq.x<dimensions && nextSq.y>=0 && nextSq.y<dimensions) {
					line.add(nextSq);
					currSq = nextSq;
					i++;
				} else { break; }
			}
			
			// remove conflicting maze tiles
			ArrayList<Point> remove = new ArrayList<Point>();
			for (Point p: line) {
				ArrayList<Point> neighbors = getNeighbors(p);
				for (Point n: neighbors) {
					if (wall.contains(n)) remove.add(p);
					break;
				}
			}
			
			for (Point p: line) {
				if (!remove.contains(p)) wall.add(p);
			}
			count = count + i;
		}
		
		// add maze tile images to screen
		tileImageViews = new ArrayList<ImageView>();
		for (Point p: wall) {
			Image pImage = new Image("textures//Tile.png",scale,scale,true,true);
			ImageView pImageView = new ImageView(pImage);
			pImageView.setX(p.x*scale);
			pImageView.setY(p.y*scale);
			tileImageViews.add(pImageView);
			root.add(pImageView);
			boardGrid[p.x][p.y] = 1;
		}
	}
	
	public ArrayList<Point> getNeighbors(Point sq) {
	// get neighbor squares of square
		return new ArrayList<Point>(Arrays.asList(new Point(sq.x-1,sq.y), 
				new Point(sq.x+1,sq.y), new Point(sq.x,sq.y-1), new Point(sq.x,sq.y+1)));
	}
}
