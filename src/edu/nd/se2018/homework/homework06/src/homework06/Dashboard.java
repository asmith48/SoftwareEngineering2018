package homework06;


import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Dashboard {
	Canvas canvasTitle;
	Canvas canvasLevel;
	Canvas canvasChips;
	
	public void draw(ObservableList<Node> root) {
	// draw dashboard and add tile
		Rectangle rect = new Rectangle(0,627,630,70);
		rect.setFill(Color.GAINSBORO);
		root.add(rect);
		
		canvasTitle = new Canvas(630,700);
		GraphicsContext gc = canvasTitle.getGraphicsContext2D();
		gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText("Chip's Challenge", 5, 630);
		root.add(canvasTitle);
	}
	
	public void addLevel(ObservableList<Node> root, int levelNum) {
	// add level number to dashboard
		canvasLevel = new Canvas(630,700);
		GraphicsContext gc = canvasLevel.getGraphicsContext2D();
		gc.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText("Level: "+levelNum, 5, 650);
		root.add(canvasLevel);
	}
	
	public void addChipCount(ObservableList<Node> root, int chipCount) {
	// add chip count to dashboard
		if (root.contains(canvasChips)) root.remove(canvasChips);
		
		canvasChips = new Canvas(630, 700);
		GraphicsContext gc = canvasChips.getGraphicsContext2D();
		gc.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText("Chips Remaining: "+chipCount, 5, 670);
		root.add(canvasChips);
	}
}
