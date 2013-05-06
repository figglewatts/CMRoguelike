package uk.uncodedstudios.CMRoguelike;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import uk.uncodedstudios.CMRoguelike.util.EntityUtil;
import uk.uncodedstudios.CMRoguelike.util.MapUtil;
import uk.uncodedstudios.uncode2d.camera.Camera;
import uk.uncodedstudios.uncode2d.tileengine.Tile;

/**
 * A generic entity, is used for the player, a monster an item OR STAIRS
 */
public class Entity {
	private String name;
	private int xPos;
	private int yPos;
	private Texture entityTexture;
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
		return xPos / Tile.RenderTileWidth;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public int getyPosInTiles() {
		return yPos / Tile.RenderTileHeight;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public Texture getEntityTexture() {
		return entityTexture;
	}
	public void setEntityTexture(Texture entityTexture) {
		this.entityTexture = entityTexture;
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
		this.entityTexture = entityTexture;
		CMRoguelike.entityList.add(this);
	}
	
	public void move(int dx, int dy)
	{
		dx *= Tile.RenderTileWidth;
		dy *= Tile.RenderTileHeight;
		if (!MapUtil.IsBlocked((this.xPos + dx)/Tile.RenderTileWidth, (this.yPos + dy)/Tile.RenderTileHeight)) {
			this.xPos += dx;
			this.yPos += dy;
		}
	}
	
	public void draw(SpriteBatch batch) {
		Vector2 offset = new Vector2(Camera.Location.x % this.xPos, Camera.Location.y % this.yPos);
		int offsetX = (int)offset.x;
		int offsetY = (int)offset.y;
		
		if (this.canDraw) {
			if (MapUtil.IsInFOV(this.getxPosInTiles(), this.getyPosInTiles())) {
				batch.draw(this.entityTexture, this.xPos - offsetX, this.yPos - offsetY, this.entityTexture.getWidth()*this.scale, this.entityTexture.getHeight()*this.scale);
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
		
		return ((float)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) / Tile.RenderTileWidth;
	}
	
	public void kill() {
		this.canDraw = false;
		this.setEntityTexture(null);
		this.setName("null");
	}
}
