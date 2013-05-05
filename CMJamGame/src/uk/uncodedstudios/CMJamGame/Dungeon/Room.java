package uk.uncodedstudios.CMJamGame.Dungeon;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Room {
	// TODO: Put some stuff here
	private int roomX;
	private int roomY;
	private int roomWidth;
	private int roomHeight;
	private Rectangle roomBounds;
	private List<Vector2> roomEntrancePoints = new ArrayList<Vector2>();
	public int getRoomX() {
		return roomX;
	}
	public void setRoomX(int roomX) {
		this.roomX = roomX;
	}
	public int getRoomY() {
		return roomY;
	}
	public void setRoomY(int roomY) {
		this.roomY = roomY;
	}
	public int getRoomWidth() {
		return roomWidth;
	}
	public void setRoomWidth(int roomWidth) {
		this.roomWidth = roomWidth;
	}
	public int getRoomHeight() {
		return roomHeight;
	}
	public void setRoomHeight(int roomHeight) {
		this.roomHeight = roomHeight;
	}
	public Rectangle getRoomBounds() {
		return roomBounds;
	}
	public void setRoomBounds(Rectangle roomBounds) {
		this.roomBounds = roomBounds;
	}
	public void setRoomBounds(int x, int y, int w, int h)
	{
		this.roomBounds = new Rectangle(x, y, w, h);
	}
	
	public void addToEntrancePoints(Vector2 position)
	{
		roomEntrancePoints.add(position);
	}
	public void addToEntrancePoints(int x, int y)
	{
		roomEntrancePoints.add(new Vector2(x, y));
	}
	public Vector2 getCenter()
	{
		return new Vector2((this.roomX + (this.roomWidth / 2)), (this.roomY + (this.roomHeight / 2)));
	}
	
	public Room(int x, int y, int w, int h)
	{
		this.roomX = x;
		this.roomY = y;
		this.roomWidth = w;
		this.roomHeight = h;
		this.roomBounds = new Rectangle(x, y, w, h);
	}
	public Room(int x, int y, int w, int h, ArrayList<Vector2> entrancePoints)
	{
		this.roomX = x;
		this.roomY = y;
		this.roomWidth = w;
		this.roomHeight = h;
		this.roomEntrancePoints = entrancePoints;
		this.roomBounds = new Rectangle(x, y, w, h);
	}
}
