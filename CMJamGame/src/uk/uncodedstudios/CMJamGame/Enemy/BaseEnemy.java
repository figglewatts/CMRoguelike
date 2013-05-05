package uk.uncodedstudios.CMJamGame.Enemy;

import uk.uncodedstudios.CMJamGame.CMJamGame;
import uk.uncodedstudios.CMJamGame.Entity;
import uk.uncodedstudios.CMJamGame.Entities.Fighter;
import uk.uncodedstudios.CMJamGame.util.MapUtil;

public class BaseEnemy extends Fighter {

	// TODO: add speed

	public BaseEnemy(String name, int x, int y, int defense, int attack, int health, int mana) {
		super(name, x, y, defense, attack, health, mana);
		// TODO Auto-generated constructor stub
	}
	
	public void takeTurn() {
		if (MapUtil.IsInFOV(this.getxPosInTiles(), this.getyPosInTiles())) {
			// move towards the player if far away
			System.out.println("Distance from player: " + this.distanceTo(CMJamGame.player));
			if (this.distanceTo(CMJamGame.player) >= 2) {
				this.moveTowards(CMJamGame.player.getxPos(), CMJamGame.player.getyPos());
			} else if (CMJamGame.player.getHealth() > 0) {
				// close enough, attack!
				// but make sure the player is still alive
				this.attack(CMJamGame.player);
			}
		}
	}	
}
