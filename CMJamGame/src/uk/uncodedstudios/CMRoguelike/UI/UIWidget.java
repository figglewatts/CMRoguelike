package uk.uncodedstudios.CMRoguelike.UI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class UIWidget {
	private String ID;
	private boolean visible;
	private Vector2 position;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public int getXPos() {
		return (int)this.position.x;
	}
	public int getYPos() {
		return (int)this.position.y;
	}
	
	public UIWidget(String id, Vector2 pos) {
		ID = id;
		position = pos;
		visible = false;
	}
	
	public void Update() { }
	
	public void Draw(SpriteBatch batch) { }
}
