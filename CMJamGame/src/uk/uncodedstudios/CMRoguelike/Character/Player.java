package uk.uncodedstudios.CMRoguelike.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import uk.uncodedstudios.CMRoguelike.CMRoguelike;
import uk.uncodedstudios.CMRoguelike.Entity;
import uk.uncodedstudios.CMRoguelike.Dungeon.Dungeon;
import uk.uncodedstudios.CMRoguelike.Entities.Fighter;
import uk.uncodedstudios.CMRoguelike.util.MapUtil;
import uk.uncodedstudios.uncode2d.graphics.*;
import uk.uncodedstudios.uncode2d.tileengine.Level;
import uk.uncodedstudios.uncode2d.tileengine.Tile;
import uk.uncodedstudios.uncode2d.camera.Camera;

public class Player extends Fighter{
	
	private int hunger;
	
	private final int CAMERA_X_BUFFER = 20*Tile.RenderTileWidth;
	private final int CAMERA_Y_BUFFER = 20*Tile.RenderTileHeight;
	
	public boolean canMoveOrAttack = true;
	
	public int xSample;
	public int ySample;
	
	public enum Race {
		HUMAN, ELF, DWARF;
	}
	
	private Race playerRace;

	// TODO: Skills

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}
	
	public Race getPlayerRace() {
		return playerRace;
	}

	public void setPlayerRace(Race playerRace) {
		this.playerRace = playerRace;
	}

	public Player(String name, int x, int y, int defense, int attack, int health, int mana) {
		super(name, x, y, defense, attack, health, mana);
		if (this.playerRace == Race.HUMAN) {
			this.setHealth(30);
			this.setDefense(2);
			this.setAttack(2);
			this.setMana(10);
		} else if (this.playerRace == Race.ELF) {
			this.setHealth(20);
			this.setDefense(2);
			this.setAttack(2);
			this.setMana(30);
		} else if (this.playerRace == Race.DWARF) {
			this.setHealth(40);
			this.setDefense(3);
			this.setAttack(3);
			this.setMana(5);
		}
	}

	public void draw(SpriteBatch batch)
	{
		super.draw(batch);
	}
	
	public void moveOrAttack(int dx, int dy) {
		if (canMoveOrAttack) {
			// the coords the player is moving to/attacking
			int x = this.getxPos() + dx*Tile.RenderTileWidth;
			int y = this.getyPos() + dy*Tile.RenderTileHeight;
			
			System.out.println("X: " + x + ", Y: " + y);
			
			Fighter target = new Fighter("null", 0, 0, 0, 0, 0, 0);
			Entity[] entityList = CMRoguelike.entityList.toArray(new Entity[CMRoguelike.entityList.size()]);
			for (Entity entity : entityList)
			{
				if (entity instanceof Fighter) {
					if (entity.getxPosInTiles() == this.getxPosInTiles() + dx &&
							entity.getyPosInTiles() == this.getyPosInTiles() + dy) {
						target = (Fighter)entity;
						break;
					}
				}
			}
			
			if (target.getName() != "null" && target != this) {
				CMRoguelike.player.attack(target);
			} else {
				super.move(dx, dy);
				UpdateCamera();
			}
			canMoveOrAttack = false;
			CMRoguelike.turnTake = true;
		}
	}
	
	public void UpdateCamera() {
		if (this.getxPos() < Camera.Location.x + CAMERA_X_BUFFER) {
			Camera.Location.x -= Tile.RenderTileWidth;
		}
		if (this.getxPos() > (Camera.Location.x + Gdx.graphics.getWidth()) - CAMERA_X_BUFFER) {
			Camera.Location.x += Tile.RenderTileWidth;
		}
		if (this.getyPos() < Camera.Location.y + CAMERA_Y_BUFFER) {
			Camera.Location.y -= Tile.RenderTileHeight;
		}
		if (this.getyPos() > (Camera.Location.y + Gdx.graphics.getHeight()) - CAMERA_Y_BUFFER) {
			Camera.Location.y += Tile.RenderTileHeight;
		}
		System.out.println("X: " + this.getxPos() + " Rest: " + (Camera.Location.x + CAMERA_X_BUFFER));
	}
	
	public void FOV(Level myMap) {
		double x, y, ax, ay, xInTiles, yInTiles;
		MapUtil.ClearMapToNotViewable();
		// iterate through each of the 360 degrees
		for (int i = 0; i < 360; i++)
		{
			/* i needs to be converted to radians, then the sine of it
			 * needs to be worked out, so that the ray casted will travel
			 * in the right direction
			 * the sine of i is how much (along the x axis) the ray needs to travel
			 * each time it's iterated
			 * of course, this value will be different based on the angle
			 * specified
			 *
			 * Interpret this as:
			 * "Distance to travel on X axis = Sine of the ray direction in radians
			 * multiplied by the pixel width each tile is rendered as"
			 */ 
			ax = Math.sin(i * 0.01745F)*Tile.RenderTileWidth;
			/* ay is how much to add on the y axis each time the ray travels
			 * it's the same as before (convert to radians etc.)
			 * but this time with cosine, because it's the Y axis
			 */
			ay = Math.cos(i * 0.01745F)*Tile.RenderTileHeight;
			
			// get the X and Y position of the player
			// add half of the RenderTileWidth/Height to make sure it's centered
			x = (this.getxPos() + Tile.RenderTileWidth / 2);
			y = (this.getyPos() + Tile.RenderTileHeight / 2);
			
			// iterates how far the ray can travel
			for (int z = 0; z < 9; z++)
			{
				x += ax; // add the distance to travel along the X axis
				y += ay; // add the distance to travel along the Y axis
				
				xSample = (int)x;
				ySample = (int)y;
				
				// if we hit the edge of the map, stop travelling
				if (x < 0 || y < 0 || x > myMap.MapWidth*Tile.RenderTileWidth || y > myMap.MapHeight*Tile.RenderTileHeight) {
					break;
				}
				
				// convert the X and Y pixel positions to tile positions
				xInTiles = x/Tile.RenderTileWidth;
				yInTiles = y/Tile.RenderTileHeight;
				
				/* set the player's position to be explored and viewable
				 * this needs to be done, otherwise the tile the player is
				 * on will be out of sight
				 */
				myMap.Rows.get(this.getyPosInTiles()).Columns.get(this.getxPosInTiles()).setIsExplored(true);
				myMap.Rows.get(this.getyPosInTiles()).Columns.get(this.getxPosInTiles()).setIsViewable(true);
				
				// set the tiles we hit to be explored and viewable
				myMap.Rows.get((int)yInTiles).Columns.get((int)xInTiles).setIsExplored(true);
				myMap.Rows.get((int)yInTiles).Columns.get((int)xInTiles).setIsViewable(true);
				
				// if we hit a solid wall, stop travelling
				if (myMap.Rows.get((int)yInTiles).Columns.get((int)xInTiles).getIsSolid()) {
					break;
				}
			}
		}
	}
}
