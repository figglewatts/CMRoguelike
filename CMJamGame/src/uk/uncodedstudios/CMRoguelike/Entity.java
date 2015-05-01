package uk.uncodedstudios.CMRoguelike;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import uk.uncodedstudios.CMRoguelike.util.EntityUtil;
import uk.uncodedstudios.CMRoguelike.util.MapUtil;
import figglewatts.slagd.graphics.tile.*;

/**
 * A generic entity, is used for the player, a monster an item OR STAIRS
 */
public class Entity {
	private String name;
	private int xPos;
	private int yPos;
	private List<Texture> textures = new ArrayList<Texture>();
	private float scale = 2;
	private boolean blocksMovement = true;
	private boolean canDraw = true;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getxPos() {
		return xPos;
	}
	public int getxPosInTiles() {
		return xPos / Tile.TILE_WIDTH;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public int getyPosInTiles() {
		return yPos / Tile.TILE_HEIGHT;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public Texture getEntityTexture(int index) {
		return textures.get(index);
	}
	public void addEntityTexture(Texture entityTexture) {
		this.textures.add(entityTexture);
	}
	public void removeEntityTexture(Texture entityTexture) {
		this.textures.remove(entityTexture);
	}
	public void removeEntityTexture(int index) {
		this.textures.remove(index);	
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public boolean isBlocksMovement() {
		return blocksMovement;
	}
	public void setBlocksMovement(boolean blocksMovement) {
		this.blocksMovement = blocksMovement;
	}
	public boolean isCanDraw() {
		return canDraw;
	}
	public void setCanDraw(boolean canDraw) {
		this.canDraw = canDraw;
	}
	public Entity() { }
	public Entity(String name, int x, int y)
	{
		this.name = name;
		this.xPos = x;
		this.yPos = y;
		CMRoguelike.entityList.add(this);
	}
	public Entity(String name, int x, int y, Texture entityTexture)
	{
		this.name = name;
		this.xPos = x;
		this.yPos = y;
		this.addEntityTexture(entityTexture);
		CMRoguelike.entityList.add(this);
	}
	
	public void move(int dx, int dy)
	{
		dx *= Tile.TILE_WIDTH;
		dy *= Tile.TILE_HEIGHT;
		if (!MapUtil.IsBlocked((this.xPos + dx)/Tile.TILE_WIDTH, (this.yPos + dy)/Tile.TILE_HEIGHT)) {
			this.xPos += dx;
			this.yPos += dy;
		}
	}
	
	public void draw(SpriteBatch batch, boolean drawAscii) {
		Vector2 offset = new Vector2(CMRoguelike.camera.position.x % this.xPos, CMRoguelike.camera.position.y % this.yPos);
		int offsetX = (int)offset.x;
		int offsetY = (int)offset.y;
		
		if (this.canDraw) {
			if (MapUtil.IsInFOV(this.getxPosInTiles(), this.getyPosInTiles())) {
				if (drawAscii && this.textures.size() > 1) {
					batch.draw(this.getEntityTexture(1), this.xPos - offsetX, this.yPos - offsetY, this.getEntityTexture(1).getWidth()*this.scale, this.getEntityTexture(1).getHeight()*this.scale);
				} else {
					batch.draw(this.getEntityTexture(0), this.xPos - offsetX, this.yPos - offsetY, this.getEntityTexture(0).getWidth()*this.scale, this.getEntityTexture(0).getHeight()*this.scale);
				}
			}
		}
	}
	
	public void moveTowards(int targetX, int targetY) {
		int dx = targetX - this.xPos;
		int dy = targetY - this.yPos;
		float distance = (float)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		
		dx = (int)Math.round(dx / distance);
		dy = (int)Math.round(dy / distance);
		this.move(dx, dy);
	}
	
	public float distanceTo(Entity other)
	{
		int dx = other.getxPos() - this.xPos;
		int dy = other.getyPos() - this.yPos;
		
		return ((float)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) / Tile.TILE_HEIGHT;
	}
	
	public void kill() {
		this.canDraw = false;
		this.setName("dead");
	}
}
