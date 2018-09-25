package model.infrastructure;

import java.awt.Point;


/**
 * Railway Tracks (Entity Object)
 * @author Jane Cleland-Huang
 *
 */
public class RailwayTracks {
	
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	Direction direction;
	
	public RailwayTracks(){}
	
	public RailwayTracks(Point startPoint, Point endPoint, Direction direction){
		startX = startPoint.x;
		startY = startPoint.y;
		endX = endPoint.x;
		endY = endPoint.y;
		this.direction = direction;
	}
	
	public int getStartX(){
		return startX;
	}
	
	public int getEndX(){
		return endX;
	}
	
	public int getStartY(){
		return startY;
	}
	
	public int getEndY(){
		return endY;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	@Override 
	public String toString(){
		return "Tracks from (" + startX + "," + startY + ") to (" + endX + "," + endY + ")";
	}
}
