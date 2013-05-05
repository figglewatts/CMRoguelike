package uk.uncodedstudios.CMJamGame.util;

import uk.uncodedstudios.CMJamGame.CMJamGame;
import uk.uncodedstudios.CMJamGame.Entity;
import uk.uncodedstudios.CMJamGame.Dungeon.Dungeon;

public class MapUtil {
	private MapUtil() { }
	
	public static boolean IsBlocked(int x, int y)
	{
		if (Dungeon.dungeon.Rows.get(y).Columns.get(x).getIsSolid()) {
			return true;
		}
		
		for (Entity entity : CMJamGame.entityList)
		{
			if (entity.isBlocksMovement() && entity.getxPosInTiles() == x && entity.getyPosInTiles() == y) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void ClearMapToNotViewable()
	{
		for (int y = 0; y < Dungeon.dungeon.Rows.size(); y++)
		{
			for (int x = 0; x < Dungeon.dungeon.Rows.get(y).Columns.size(); x++)
			{
				Dungeon.dungeon.Rows.get(y).Columns.get(x).setIsViewable(false);
			}
		}
	}
	
	public static void ClearMapToNotVisible()
	{
		for (int y = 0; y < Dungeon.dungeon.Rows.size(); y++)
		{
			for (int x = 0; x < Dungeon.dungeon.Rows.get(y).Columns.size(); x++)
			{
				Dungeon.dungeon.Rows.get(y).Columns.get(x).setIsExplored(false);
			}
		}
	}
	
	public static boolean IsInFOV(int x, int y)
	{
		if (Dungeon.dungeon.Rows.get(y).Columns.get(x).getIsViewable() == true) {
			return true;
		} else { 
			return false;
		}
	}
}
