package uk.uncodedstudios.CMRoguelike.util;

import uk.uncodedstudios.CMRoguelike.CMRoguelike;
import uk.uncodedstudios.CMRoguelike.Entity;
import uk.uncodedstudios.CMRoguelike.Entities.Fighter;

public class EntityUtil {
	private EntityUtil() { }
	
	public static boolean IsEntity(int x, int y)
	{
		for(Entity entity : CMRoguelike.entityList)
		{
			if (entity.getxPosInTiles() == x && entity.getyPosInTiles() == y) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean IsFighter(int x, int y)
	{
		for(Entity entity : CMRoguelike.entityList)
		{
			if (entity.getxPosInTiles() == x && entity.getyPosInTiles() == y && entity instanceof Fighter) {
				return true;
			}
		}
		return false;
	}
}
