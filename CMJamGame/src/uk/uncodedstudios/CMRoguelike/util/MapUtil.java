package uk.uncodedstudios.CMRoguelike.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import figglewatts.slagd.graphics.tile.Tile;
import uk.uncodedstudios.CMRoguelike.CMRoguelike;
import uk.uncodedstudios.CMRoguelike.Entity;
import uk.uncodedstudios.CMRoguelike.Dungeon.Dungeon;
import uk.uncodedstudios.CMRoguelike.Dungeon.JSONRoom;

public class MapUtil {
	private MapUtil() { }
	
	public static boolean IsBlocked(int x, int y)
	{
		/*if (Dungeon.dungeon.Rows.get(y).Columns.get(x).getIsSolid()) {
			return true;
		}
		for (Entity entity : CMRoguelike.entityList){
			if (entity.isBlocksMovement() && entity.getxPosInTiles() == x && entity.getyPosInTiles() == y) {
				return true;
			}
		}*/
		return false;
	}
	public static boolean IsBlocked(Vector2 v)
	{
		/*if (Dungeon.dungeon.Rows.get((int)v.y).Columns.get((int)v.x).getIsSolid()) {
			return true;
		}
		for (Entity entity : CMRoguelike.entityList) {
			if (entity.isBlocksMovement() && entity.getxPosInTiles() == (int)v.x && entity.getyPosInTiles() == (int)v.y) {
				return true;
			}
		}*/
		return false;
	}
	
	public static void ClearMapToNotViewable()
	{
		/*for (int y = 0; y < Dungeon.dungeon.Rows.size(); y++)
		{
			for (int x = 0; x < Dungeon.dungeon.Rows.get(y).Columns.size(); x++)
			{
				Dungeon.dungeon.Rows.get(y).Columns.get(x).setIsViewable(false);
			}
		}*/
	}
	
	public static void ClearMapToNotVisible()
	{
		/*for (int y = 0; y < Dungeon.dungeon.Rows.size(); y++)
		{
			for (int x = 0; x < Dungeon.dungeon.Rows.get(y).Columns.size(); x++)
			{
				Dungeon.dungeon.Rows.get(y).Columns.get(x).setIsExplored(false);
			}
		}*/
	}
	
	public static boolean IsInFOV(int x, int y)
	{
		/*if (Dungeon.dungeon.Rows.get(y).Columns.get(x).getIsViewable() == true) {
			return true;
		} else { 
			return false;
		}*/
		return true;
	}
	
	/**
	 * Gets a list of the non solid tiles in a JSONRoom
	 */
	public static List<Vector2> GetRoomNonSolidTiles(JSONRoom room) 
	{
		List<Vector2> tileList = new ArrayList<Vector2>();
		int[][] layout = room.getLayout();
		
		for (int y = 0; y < room.getHeight(); y++) {
			for (int x = 0; x < room.getWidth(); x++) {
				if (layout[y][x] != 0) {
					tileList.add(new Vector2(x, y));
				}
			} 
		}
		if (tileList.size() > 0) {
			return tileList;
		} else {
			return null;
		}
	}
	
	/**
	 * Converts map dimensions from pixels to tiles
	 */
	public static int normalizeToTileDimensions(int num, boolean width)
	{
		if (width) {
			return (num / Tile.TILE_WIDTH);
		} else {
			return (num / Tile.TILE_HEIGHT);
		}
	}
}
