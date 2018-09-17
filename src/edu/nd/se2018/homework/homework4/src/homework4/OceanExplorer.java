package homework4;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.LinkedList;
import java.util.Random;


public class OceanExplorer extends Application {
	AnchorPane root; //pane
	OceanMap oceanMap; //grid object to display
	int scale; //scaling factor
	Ship ship; //ship object
	LinkedList<Pirate> pirates; //list of pirate objects
	int pirateCount = 2; //number of pirates
	Image shipImage;
	ImageView shipImageView;
	Scene scene; //scene

	public static void main(String[] args) {
        launch(args);
	}

	public void startSailing() {
		//move ship with key press (right, left, up, down)
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent ke) {
				switch(ke.getCode()) {
					case RIGHT:
						ship.goEast();
						break;
					case LEFT:
						ship.goWest();
						break;
					case UP:
						ship.goNorth();
						break;
					case DOWN:
						ship.goSouth();
						break;
					default:
						break;
				}
				//set ship image position
				shipImageView.setX(ship.getShipLocation().x*scale);
				shipImageView.setY(ship.getShipLocation().y*scale);
			}
		});
	}
	
	@Override
	public void start(Stage oceanStage) throws Exception {	
		Random rand = new Random();
		
		//pane
		root = new AnchorPane();
		ObservableList<Node> rootChild = root.getChildren();
		
		//oceanMap
		scale = 25; //scaling factor
		oceanMap = new OceanMap();
		oceanMap.drawMap(rootChild, scale); //draw grid on pane
		oceanMap.placeIslands(rootChild, scale); //add islands to grid
		
		//ship object
		ship = new Ship(0,0,oceanMap);
		shipImage = new Image(getClass().getResource("ColumbusShip.png").toString(),25,25,true,true);
		shipImageView = new ImageView(shipImage);
		shipImageView.setX(ship.getShipLocation().x*scale);
		shipImageView.setY(ship.getShipLocation().y*scale);
		rootChild.add(shipImageView); 
		
		//pirate objects
		pirates = new LinkedList<Pirate>(); //store pirates in list
		for (int i=0; i<pirateCount; i++) {
			pirates.add(new Pirate(rand.nextInt(25) + 0,rand.nextInt(25) + 0, scale, oceanMap));
		}
		for (Pirate pirate: pirates) {
			ship.addObserver(pirate); //add pirates as observers of ship 
			rootChild.add(pirate.getImageView());
		}
		
		//scene
		scene = new Scene(root,630,630);
		oceanStage.setScene(scene);
		oceanStage.setTitle("Ocean Explorer");
		
		oceanStage.show();
		
		//move ship
		startSailing();
	}

}
