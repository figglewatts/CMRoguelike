package uk.uncodedstudios.CMRoguelike.Dungeon;

import uk.uncodedstudios.CMRoguelike.JSON.JSONObject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Figglewatts
 */
public class JSONRoom extends JSONObject {
	private int width;
	private int height;
	private String name;
	private int[][] layout;
	private int type;
	private Rectangle roomBounds;
	
	public String toString() {
		return Integer.toString(this.getX()) + ", " + Integer.toString(this.getY()) + ", " + Integer.toString(width) + ", " + Integer.toString(height) + ", " + name;
	}
	
	public String getLayoutAsString() {
		StringBuilder string = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				string.append(Integer.toString(layout[y][x]));
				string.append(", ");
			}
			string.append("\n");
		}
		return string.toString();
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[][] getLayout() {
		return layout;
	}
	public void setLayout(int[][] layout) {
		this.layout = layout;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getTileIDFromPos(int x, int y) {
		try {
			return layout[y][x];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
	}
	
	public Vector2 getCenter() {
		return new Vector2((int)(this.getX() +(width / 2)), (int)(this.getY() + (height / 2)));
	}
	
	public Rectangle getRoomBounds() {
		return roomBounds;
	}
	public void setRoomBounds(int x, int y) {
		this.roomBounds = new Rectangle(x, y, this.width, this.height);
	}
	
	public int getX() {
		return (int)roomBounds.x;
	}
	public int getY() {
		return (int)roomBounds.y;
	}

	public JSONRoom() { }
}
