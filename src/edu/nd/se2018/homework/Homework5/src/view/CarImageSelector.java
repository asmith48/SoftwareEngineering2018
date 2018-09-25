package view;

import javafx.scene.image.Image;

public class CarImageSelector {
	

	public static Image getImage(){
		int imageSize = 20;
		int pickNum = (int)(Math.random() * 4);
		Image img;
		switch(pickNum){
		case 0: img = new Image("bluecar.png",imageSize,imageSize,false,true);
		break;
		case 2: img = new Image("graycar.png",imageSize,imageSize,false,true);
		break;
		case 3: img = new Image("greencar.png",imageSize,imageSize,false,true);
		break;
		default: img = new Image("redcar.png",imageSize,imageSize,false,true);	
		}
		return img;	
	}
}
