package homework4;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class OceanMap {
	int[][] oceanGrid = new int[25][25];
	final int dimensions = 25;

	
	public void drawMap(ObservableList<Node> root, int scale) {
		//draw 25x25 grid, all squares contain 0 value to show that they're empty
		for (int x=0; x<dimensions; x++) {
			for (int y=0; y<dimensions; y++) {
				Rectangle rect = new Rectangle(x*scale, y*scale, scale, scale);
				rect.setStroke(Color.BLACK);
				rect.setFill(Color.LIGHTSKYBLUE);
				root.add(rect);
				oceanGrid[x][y] = 0; //empty square
			}
		}
	}
	
	public void placeIslands(ObservableList<Node> root, int scale) {
		//place islands on grid at 10 random squares
		Random rand = new Random();
		for (int i=0; i<10; i++) {
			int x = rand.nextInt(dimensions) + 0;
			int y = rand.nextInt(dimensions) + 0;
			while(oceanGrid[x][y] == 1) {
				//don't duplicate island position
				x = rand.nextInt(dimensions) + 0;
			}
			Rectangle rect = new Rectangle(x*scale, y*scale, scale, scale);
			rect.setStroke(Color.BLACK);
			rect.setFill(Color.DARKCYAN);
			root.add(rect);
			oceanGrid[x][y] = 1;
		}
	}
}
