package homework06;

import javafx.application.Application;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;


public class Main extends Application {
	AnchorPane root; //pane
	Scene scene; //scene
	BoardMap boardMap;
	Dashboard dashboard;
	int scale = 25;   //scaling factor
	Player player;
	Image playerImage;
	ImageView playerImageView;
	ObservableList<Node> rootChild;
	int levelNum;
	LinkedList<Chip> chips = new LinkedList<Chip>();
	LinkedList<Bug> bugs;
	int bugCount;
	ImageView bugIV;
	LinkedList<ImageView> bugImageViews;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void levelCreator(Stage boardStage, int levelNum) {
	//create each level: place player, chips, bugs, and portal, and fill dashboard 
		
		if (levelNum >= 3) {
			boardStage.close();
			System.out.println("YOU WIN!");
		}
		else if (levelNum > 1) {
			resetLevel(); //remove old image views from screen
		}
		
		// draw maze on game board and place portal
		boardMap.createMaze(rootChild, scale, levelNum);
		boardMap.createPortal(rootChild, scale);
		//System.out.println("H "+boardMap.getWallIV());
		
		// write level number to dashboard
		dashboard.addLevel(rootChild, levelNum);
		 
		// player object and image
		player = new Player(scale, boardMap);
		playerImageView = player.getImageView();
		rootChild.add(playerImageView); 
		
		// create list of chip objects 
		chips = new LinkedList<Chip>();  
		int chipCount = 4; //number of chips to create
		for (int i=0; i<chipCount; i++) {
			chips.add(new Chip(scale, boardMap, rootChild));
		}
		// add chips as observers of player
		for (Chip chip: chips) {
			player.addObserver(chip); 
		}
		
		// create list of bug objects and list of bug image views
		bugs = new LinkedList<Bug>();
		bugImageViews = new LinkedList<ImageView>();
		if (levelNum==1) bugCount = 3; //create 3 bugs for first level
		else bugCount = 6; //create 6 bugs for second level
		boolean vertical = true; //decide whether bug will move vertically or horizontally
		
		for (int i=0; i<bugCount; i++) {
			Bug bug = new Bug(scale, boardMap, rootChild, vertical);
			vertical = !vertical;
			bugs.add(bug);
			
			bugIV = bug.getImageView();
			bugImageViews.add(bugIV);
			rootChild.add(bugIV);
		}
		//add player as observer of each bug
		for (Bug bug: bugs) {
			bug.addObserver(player);
		}
		
		//control animation of player and bug objects
		movePlayer(boardStage);
	}
	
	public void resetLevel() {
	// remove all image views of past level from screen
		// remove all board tiles
		//System.out.println("A "+boardMap.getWallIV());
		for (ImageView wallIV : boardMap.getWallIV()) rootChild.remove(wallIV);
		rootChild.remove(boardMap.getPortalIV()); //remove portal
		rootChild.remove(dashboard.canvasLevel); // remove level from dashboard
		rootChild.remove(dashboard.canvasChips); // remove chip count from dashboard
		rootChild.remove(playerImageView); //remove player
		for (Chip chip: chips) rootChild.remove(chip.getImageView()); //remove chips
		for (Bug bug: bugs) rootChild.remove(bug.getImageView()); //remove bugs
		
		// reset all tiles on board grid to 0 value
		for (int x=0; x<boardMap.dimensions; x++) {
			for (int y=0; y<boardMap.dimensions; y++) {
				boardMap.boardGrid[x][y] = 0;
			}
		}
	}
	
	public void movePlayer(Stage boardStage) {
	// control animation of player and bugs
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		// control animation of bugs to occur continuously
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
            public void handle(long timeCurr) {
				for (Bug bug: bugs) {
					bug.move();
				}
			
				for (ImageView bugIV: bugImageViews) {
					rootChild.remove(bugIV);
				}
				bugImageViews.clear();
				
				for (Bug bug: bugs) {
					bugImageViews.add(bug.getImageView());
					rootChild.add(bug.getImageView());
				}
				
				// check if player and bug collide 
				if (player.gameOver) {
					boardStage.close();
					System.out.println("Ran into bug. GAME OVER.");
				}
				
				// keep track of chips remaining to be collected and display on dashboard
				int chipsRemaining = 0;
				for (Chip chip: chips) if (!chip.chipCollected()) chipsRemaining++;
				dashboard.addChipCount(rootChild, chipsRemaining);
				
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		// control animation of player by keyboard input
		EventHandler keyEvents = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
				scene.setOnKeyPressed(new EventHandler<KeyEvent>() {					
					@Override
					public void handle(KeyEvent ke) {
						switch(ke.getCode()) {
						case RIGHT:
							player.goEast();
							break;
						case LEFT:
							player.goWest();
							break;
						case UP:
							player.goNorth();
							break;
						case DOWN:
							player.goSouth();
							break;
						case ESCAPE:
							boardStage.close();
						default:
							break;
						}
						
						//set player image position
						rootChild.remove(playerImageView);
						playerImageView = player.getImageView();
						rootChild.add(playerImageView); 
						
						// enter next level
						if (checkFinish()) {
							//System.out.println("HERE");
							levelCreator(boardStage, ++levelNum); 
						}
					}
				});
            } 
		};
				
		Duration duration = Duration.millis(2000);	
		KeyFrame keyFrame = new KeyFrame(duration, keyEvents);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		timer.start();
	}
	
	public boolean checkFinish() {
	// check that player is on same tile as portal object
		Point playerLocation = player.getPlayerLocation();
		Point portalLocation = boardMap.getPortalLocation();
		if (playerLocation.x == portalLocation.x && playerLocation.y == portalLocation.y
				&& checkAllChipsCollected()) {
			return true;
		}
		return false;
	}
	
	public boolean checkAllChipsCollected() {
	// check if all chips have been collected
		for (Chip chip: chips) {
			if (!chip.chipCollected()) return false; 
		}
		return true;
	}
	
	@Override
	public void start(Stage boardStage) throws Exception {	
		//pane
		root = new AnchorPane();
		rootChild = root.getChildren();
		
		//create game board
		boardMap = new BoardMap();
		boardMap.drawMap(rootChild, scale); 
		
		//create dashboard
		dashboard = new Dashboard();
		dashboard.draw(rootChild);
		
		//scene
		scene = new Scene(root,630,700);
		boardStage.setScene(scene);
		boardStage.setTitle("Chip's Challenge");
				
		boardStage.show();
		
		levelNum = 1;
		levelCreator(boardStage, levelNum);		
	}

}
