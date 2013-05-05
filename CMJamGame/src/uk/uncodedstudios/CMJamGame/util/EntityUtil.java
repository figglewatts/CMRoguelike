package uk.uncodedstudios.CMJamGame.util;

import uk.uncodedstudios.CMJamGame.CMJamGame;
import uk.uncodedstudios.CMJamGame.Entity;
import uk.uncodedstudios.CMJamGame.Entities.Fighter;

public class EntityUtil {
	private EntityUtil() { }
	
	public static boolean IsEntity(int x, int y)
	{
		for(Entity entity : CMJamGame.entityList)
		{
			if (entity.getxPosInTiles() == x && entity.getyPosInTiles() == y) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean IsFighter(int x, int y)
	{
		for(Entity entity : CMJamGame.entityList)
		{
			if (entity.getxPosInTiles() == x && entity.getyPosInTiles() == y && entity instanceof Fighter) {
				return true;
			}
		}
		return false;
	}
}
