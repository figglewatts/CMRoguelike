package uk.uncodedstudios.CMJamGame.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import uk.uncodedstudios.CMJamGame.CMJamGame;
import uk.uncodedstudios.CMJamGame.Entity;
import uk.uncodedstudios.CMJamGame.Dungeon.Dungeon;
import uk.uncodedstudios.CMJamGame.Entities.Fighter;
import uk.uncodedstudios.CMJamGame.util.MapUtil;
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

	// TODO: Skills

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}
	
	public Player(String name, int x, int y, int defense, int attack, int health, int mana) {
		super(name, x, y, defense, attack, health, mana);
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
			Entity[] entityList = CMJamGame.entityList.toArray(new Entity[CMJamGame.entityList.size()]);
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
				CMJamGame.player.attack(target);
			} else {
				super.move(dx, dy);
				UpdateCamera();
			}
			canMoveOrAttack = false;
			CMJamGame.turnTake = true;
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
		double x, y, ax, ay;
		MapUtil.ClearMapToNotViewable();
		for (int i = 0; i < 360; i++)
		{
			ax = Math.sin(i * 0.01745F);
			ay = Math.cos(i * 0.01745F);
			
			x = (this.getxPos() + Tile.RenderTileWidth / 2) / Tile.RenderTileWidth;
			y = (this.getyPos() + Tile.RenderTileHeight / 2) / Tile.RenderTileHeight;
			
			for (int z = 0; z < 9; z++)
			{
				x += ax;
				y += ay;
				
				xSample = (int)x;
				ySample = (int)y;
				
				if (x < 0 || y < 0 || x > myMap.MapWidth || y > myMap.MapHeight) {
					//System.out.println("Hit edge of map while computing.");
					break;
				}
				
				//System.out.println("X: " + x + ", Y: " + y);
				
				myMap.Rows.get((int)y).Columns.get((int)x).setIsExplored(true);
				myMap.Rows.get((int)y).Columns.get((int)x).setIsViewable(true);
				
				if (myMap.Rows.get((int)y).Columns.get((int)x).getIsSolid()) {
					//System.out.println("Hit solid wall while computing.");
					break;
				}
				
				//System.out.println(myMap.Rows.get((int)y).Columns.get((int)x).getIsExplored());
			}
		}
	}
}
