package uk.uncodedstudios.CMRoguelike.Enemy;

import uk.uncodedstudios.CMRoguelike.CMRoguelike;
import uk.uncodedstudios.CMRoguelike.Entity;
import uk.uncodedstudios.CMRoguelike.Entities.Fighter;
import uk.uncodedstudios.CMRoguelike.util.MapUtil;

public class BaseEnemy extends Fighter {

	// TODO: add speed

	public BaseEnemy(String name, int x, int y, int defense, int attack, int health, int mana) {
		super(name, x, y, defense, attack, health, mana);
		// TODO Auto-generated constructor stub
	}
	
	public void takeTurn() {
		if (MapUtil.IsInFOV(this.getxPosInTiles(), this.getyPosInTiles())) {
			// move towards the player if far away
			System.out.println("Distance from player: " + this.distanceTo(CMRoguelike.player));
			if (this.distanceTo(CMRoguelike.player) >= 2) {
				this.moveTowards(CMRoguelike.player.getxPos(), CMRoguelike.player.getyPos());
			} else if (CMRoguelike.player.getHealth() > 0) {
				// close enough, attack!
				// but make sure the player is still alive
				this.attack(CMRoguelike.player);
			}
		}
	}	
}
